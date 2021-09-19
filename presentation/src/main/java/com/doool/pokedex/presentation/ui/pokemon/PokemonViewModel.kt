package com.doool.pokedex.presentation.ui.pokemon

import androidx.lifecycle.ViewModel
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.LoadPokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
  private val getPokemon: GetPokemon,
  private val loadPokemonList: LoadPokemonList
) : ViewModel() {

  val pokemonList = flow {
    val result = loadPokemonList(0)
    result.fold({
      emit(it.mapNotNull {
        getPokemon(it.name).getOrNull()
      })
    }, {
      it
    })
  }
}