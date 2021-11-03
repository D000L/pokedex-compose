package com.doool.pokedex.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.usecase.search.Search
import com.doool.pokedex.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUIModel(
  var pokemon: List<Pair<PokemonDetail, PokemonSpecies>> = emptyList(),
  var items: List<Item> = emptyList(),
  var moves: List<PokemonMove> = emptyList()
)

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchUsacese: Search
) : BaseViewModel() {

  companion object {
    private const val SEARCH_ITEM_LIMIT = 6
  }

  val query = MutableStateFlow("")

  val searchResultState = query.distinctUntilChangedBy { it }.flatMapLatest {
    searchUsacese(Pair(it, SEARCH_ITEM_LIMIT))
  }.map {
    when (it) {
      is LoadState.Error -> LoadState.Error()
      is LoadState.Loading -> LoadState.Loading()
      is LoadState.Success -> LoadState.Success(
        SearchUIModel(
          it.data.pokemon,
          it.data.item,
          it.data.move
        )
      )
    }
  }

  fun search(query: String) {
    viewModelScope.launch {
      this@SearchViewModel.query.emit(query)
    }
  }

  fun clearQuery() {
    query.tryEmit("")
  }
}