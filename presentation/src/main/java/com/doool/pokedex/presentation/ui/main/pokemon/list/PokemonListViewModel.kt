package com.doool.pokedex.presentation.ui.main.pokemon.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonNames
import com.doool.pokedex.domain.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
  private val getPokemonList: GetPokemonNames,
  private val getPokemonUsecase: GetPokemon,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  private var searchQuery: String? = savedStateHandle.get<String?>(QUERY_PARAM)

  val pokemonList = flow {
    emit(getPokemonList(searchQuery))
  }

  fun getPokemon(name: String): Flow<PokemonDetail> =
    getPokemonUsecase(name).filterIsInstance<LoadState.Success<PokemonDetail>>().map {
      it.data
    }
}