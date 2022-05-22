package com.doool.pokedex.pokemon.feature.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.core.base.BaseViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.usecase.GetForm
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonList
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import com.doool.pokedex.pokemon.destination.QUERY_PARAM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList,
    private val getPokemonUsecase: GetPokemon,
    private val getPokemonSpecies: GetPokemonSpecies,
    private val getForm: GetForm,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var searchQuery: String? = savedStateHandle.get<String?>(QUERY_PARAM)

    val pokemonList = savedStateHandle.getLiveData<List<Pokemon>>("POKEMON_LIST", emptyList())

    fun loadPokemonList() {
        viewModelScope.launch {
            pokemonList.postValue(getPokemonList(searchQuery))
        }
    }

    fun getItemState(name: String) =
        getPokemonUsecase(GetPokemon.Params.ByName(name)).flatMapMerge { pokemon ->
            when (pokemon) {
                is LoadState.Error -> emptyFlow()
                is LoadState.Loading -> emptyFlow()
                is LoadState.Success -> {
                    val form = pokemon.data.name.contains("-")
                    val formFlow =
                        if (form) getForm(pokemon.data.name) else flowOf(LoadState.Success(null))
                    combine(getPokemonSpecies(pokemon.data.species.id), formFlow) { species, form ->
                        Triple(pokemon, species, form)
                    }
                }
            }
        }.map { (pokemon, species, form) ->
            if (species is LoadState.Success && form is LoadState.Success) {
                LoadState.Success(
                    PokemonListItem(
                        pokemon.data.id,
                        pokemon.data.name,
                        species.data.names,
                        form.data?.formNames ?: emptyList(),
                        pokemon.data.image,
                        pokemon.data.types
                    )
                )
            } else LoadState.Loading()
        }.onStart { delay(500) }.stateInWhileSubscribed(1000) { LoadState.Loading() }
}
