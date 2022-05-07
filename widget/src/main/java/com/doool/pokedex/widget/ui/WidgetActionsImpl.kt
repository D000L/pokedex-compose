package com.doool.pokedex.widget.ui

import android.content.Context
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
) : WidgetActions {

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
                        name = pokemon.data.name,
                        height = pokemon.data.weight,
                        weight = pokemon.data.height,
                        species = species.data.genera[0].text,
                        description = species.data.flavorText[0].text
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
}