package com.doool.pokedex.widget.ui

import android.content.Context
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.repository.SettingRepository
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class WidgetActionsImpl(
    private val getPokemon: GetPokemon,
    private val getPokemonSpecies: GetPokemonSpecies,
    private val settingRepository: SettingRepository,
) : WidgetActions {

    private var languageCode: String = EnglishCode

    companion object {
        private const val EnglishCode = "en"
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            settingRepository.getLanguageCode().collectLatest {
                languageCode = it ?: EnglishCode
            }
        }
    }

    private var job: Job? = null

    override fun loadPokemon(context: Context, id: Int) {
        job?.cancel()

        job = CoroutineScope(Dispatchers.Default).launch {
            updateWidget(context, LoadState.loading(WidgetUIModel(id)))
            loadPokemonInfo(context, id)
        }
    }

    private suspend fun loadPokemonInfo(context: Context, id: Int) {
        val getPokemon = getPokemon(GetPokemon.Params.ById(id)).onEach {
            yield()
        }
        val getSpecies = getPokemon.flatMapLatest {
            when (it) {
                is LoadState.Error -> flowOf(LoadState.failure(null))
                is LoadState.Loading -> emptyFlow()
                is LoadState.Success -> getPokemonSpecies(it.data.species.id)
            }
        }.onEach {
            yield()
        }

        combine(getPokemon, getSpecies) { pokemon, species ->
            if (pokemon is LoadState.Error || species is LoadState.Error) {
                LoadState.failure(null)
            } else if (pokemon is LoadState.Success && species is LoadState.Success) {
                LoadState.success(
                    WidgetUIModel(
                        id = id,
                        name = species.data.names.localized,
                        height = pokemon.data.weight,
                        weight = pokemon.data.height,
                        species = species.data.genera.localized,
                        description = species.data.flavorText.localized
                    )
                )
            } else null
        }.collect {
            it?.let { updateWidget(context, it) }
        }
    }

    private suspend fun updateWidget(context: Context, state: LoadState<WidgetUIModel>) {
        context.getGlanceId()?.let {
            PokedexWidget(state).update(context, it)
        }
    }

    private val List<LocalizedString>.localized: String
        get() = firstOrNull { it.language == languageCode }?.text ?: ""
}