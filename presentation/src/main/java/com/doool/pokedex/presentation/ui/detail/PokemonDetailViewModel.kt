package com.doool.pokedex.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonList
import com.doool.pokedex.presentation.paging.PokemonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
  private val getPokemon: GetPokemon,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  companion object{
    const val POKEMON_NAME = "POKEMON_NAME"
  }

  private val pokemonName = savedStateHandle.getLiveData<String>(POKEMON_NAME).asFlow()

  val pokemon : Flow<PokemonDetail> = pokemonName.transformLatest {
    getPokemon(it).fold({
      emit(it)
    },{

    })
  }
}