package com.doool.pokedex.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.usecase.search.SearchItem
import com.doool.pokedex.domain.usecase.search.SearchMove
import com.doool.pokedex.domain.usecase.search.SearchPokemon
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchResult(
  var pokemon: List<PokemonDetail> = emptyList(),
  var items: List<Item> = emptyList(),
  var moves: List<PokemonMove> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val searchMove: SearchMove,
  private val searchItem: SearchItem,
  private val searchPokemon: SearchPokemon
) : BaseViewModel() {

  companion object {
    private const val SEARCH_ITEM_LIMIT = 6
  }

  val query = MutableStateFlow("")

  val searchResultState = query.distinctUntilChangedBy { it }.flatMapLatest {
    combine(
      searchPokemon(it, SEARCH_ITEM_LIMIT),
      searchItem(it, SEARCH_ITEM_LIMIT),
      searchMove(it, SEARCH_ITEM_LIMIT),
      ::SearchResult
    ).withLoadState()
  }

  fun search(query: String) {
    viewModelScope.launch {
      this@HomeViewModel.query.emit(query)
    }
  }

  fun clearQuery(){
    query.tryEmit("")
  }
}