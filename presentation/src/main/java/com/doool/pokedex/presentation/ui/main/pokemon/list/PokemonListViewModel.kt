package com.doool.pokedex.presentation.ui.main.pokemon.list

import androidx.lifecycle.SavedStateHandle
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonNames
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
  private val getPokemonList: GetPokemonNames,
  private val getPokemonUsecase: GetPokemon,
  savedStateHandle: SavedStateHandle
) : BaseViewModel() {

  private var searchQuery: String? = savedStateHandle.get<String?>(QUERY_PARAM)

  val pokemonList = flow {
    emit(getPokemonList(searchQuery))
  }

  fun getPokemon(name: String) =
    getPokemonUsecase(name).withLoadState().stateInWhileSubscribed(1000) { LoadState.Loading }
}