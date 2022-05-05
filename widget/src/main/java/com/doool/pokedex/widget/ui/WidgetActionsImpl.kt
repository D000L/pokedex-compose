package com.doool.pokedex.widget.ui

import android.content.Context
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class WidgetActionsImpl(
    private val getPokemon: GetPokemon,
    private val getPokemonSpecies: GetPokemonSpecies,
) : WidgetActions {
    override suspend fun loadPokemon(context: Context, id: Int) {
        loadPokemonInfo(context, id)
    }

    private suspend fun loadPokemonInfo(context: Context, id: Int) {
        getPokemon(GetPokemon.Params.ById(id)).collectSuccess(context) { pokemon ->
            getPokemonSpecies(pokemon.species.id).collectSuccess(context) { species ->
                updateWidget(context, LoadState.success(WidgetUIModel(
                    id = id,
                    name = pokemon.name,
                    height = pokemon.weight,
                    weight = pokemon.height,
                    species = species.genera[0].text,
                    description = species.flavorText[0].text
                )))
            }
        }
    }

    private suspend fun <T> Flow<LoadState<T>>.collectSuccess(
        context: Context,
        onSuccess: suspend (T) -> Unit,
    ) {
        collectLatest {
            when (it) {
                is LoadState.Error -> {}
                is LoadState.Loading -> updateWidget(context, LoadState.loading(WidgetUIModel()))
                is LoadState.Success -> onSuccess(it.data)
            }
        }
    }

    private suspend fun updateWidget(context: Context, state: LoadState<WidgetUIModel>) {
        context.getGlanceId()?.let {
            PokedexWidget(state).update(context, it)
        }
    }
}