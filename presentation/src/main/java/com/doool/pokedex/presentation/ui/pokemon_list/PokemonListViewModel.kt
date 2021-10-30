package com.doool.pokedex.presentation.ui.pokemon_list

import androidx.lifecycle.SavedStateHandle
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonList
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import com.doool.pokedex.presentation.ui.pokemon_list.destination.QUERY_PARAM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
  private val getPokemonList: GetPokemonList,
  private val getPokemonUsecase: GetPokemon,
  private val getPokemonSpecies: GetPokemonSpecies,
  savedStateHandle: SavedStateHandle
) : BaseViewModel() {

  private var searchQuery: String? = savedStateHandle.get<String?>(QUERY_PARAM)

  val pokemonList = flow {
    emit(getPokemonList(searchQuery))
  }

  fun getItemState(name: String) =
    combine(getPokemonUsecase(name), getPokemonSpecies(name)) { pokemon, species ->
      PokemonListItem(pokemon.id, pokemon.name, species.names, pokemon.image, pokemon.types)
    }.onStart { delay(500) }.withLoadState().stateInWhileSubscribed(1000) { LoadState.Loading }
}