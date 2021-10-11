package com.doool.pokedex.presentation.ui.pokemon

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.usecase.GetPokemonNames
import com.doool.pokedex.domain.usecase.LoadState
import com.doool.pokedex.domain.usecase.search.SearchPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
  private val getPokemonList: GetPokemonNames,
  private val searchPokemon: SearchPokemon,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  private var searchQuery: String? = savedStateHandle.get<String?>("QUERY")

  val pokemonList = flow {
    emit(getPokemonList(searchQuery))
  }

  fun getPokemon(name: String): Flow<PokemonDetail> = flow {
    emitAll(searchPokemon(name, 1).filterIsInstance<LoadState.Success<List<PokemonDetail>>>().map {
      it.data.first()
    })
  }
}