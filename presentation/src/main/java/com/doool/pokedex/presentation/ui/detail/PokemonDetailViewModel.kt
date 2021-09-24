package com.doool.pokedex.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
  private val getPokemon: GetPokemon,
  private val getPokemonSpecies: GetPokemonSpecies,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  companion object {
    const val POKEMON_ID = "POKEMON_ID"
  }

  private val pokemonId = savedStateHandle.getLiveData<Int>(POKEMON_ID).asFlow()

//  val pokemon: Flow<PokemonDetail> = pokemonId.transformLatest {
//    getPokemon(it).fold({
//      emit(it)
//    }, {})
//  }

  val pokemonSpecies: Flow<PokemonSpecies> = pokemonId.transformLatest {
    getPokemonSpecies(it).fold({
      emit(it)
    }, {})
  }
}