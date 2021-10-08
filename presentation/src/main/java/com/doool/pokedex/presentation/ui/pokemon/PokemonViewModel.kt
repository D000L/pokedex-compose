package com.doool.pokedex.presentation.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.usecase.GetPokemonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
  private val getPokemonList: GetPokemonPagingSource
) : ViewModel() {

  private var searchQuery: String? = null
  private var pagingSource: PagingSource<Int, PokemonDetail>? = null

  val pokemonList = Pager(
    PagingConfig(
      20,
      prefetchDistance = 5,
      initialLoadSize = 20,
      enablePlaceholders = false
    )
  ) {
    getPokemonList(searchQuery).also { pagingSource = it }
  }.flow.cachedIn(viewModelScope)

  fun search(query: String) {
    viewModelScope.launch {
      searchQuery = query
      pagingSource?.invalidate()
    }
  }
}