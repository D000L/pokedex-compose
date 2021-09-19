package com.doool.pokedex.presentation.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.doool.pokedex.domain.usecase.GetPokemonList
import com.doool.pokedex.presentation.paging.PokemonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
  private val getPokemonList: GetPokemonList
) : ViewModel() {

  val pokemonList = Pager(PagingConfig(20, prefetchDistance = 10)) {
    PokemonPagingSource(getPokemonList)
  }.flow
}