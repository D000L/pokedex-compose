package com.doool.pokedex.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.search.SearchItem
import com.doool.pokedex.domain.usecase.search.SearchMove
import com.doool.pokedex.domain.usecase.search.SearchPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUIState(
  var isLoading: Boolean = false,
  var isError: Boolean = false,
  var pokemon: List<PokemonDetail>? = null,
  var items: List<Item>? = null,
  var moves: List<PokemonMove>? = null
) {
  fun process(
    pokemon: LoadState<List<PokemonDetail>>,
    items: LoadState<List<Item>>,
    moves: LoadState<List<PokemonMove>>
  ): SearchUIState {
    val isError = pokemon is LoadState.Error || items is LoadState.Error || moves is LoadState.Error
    val isLoading =
      pokemon is LoadState.Loading || items is LoadState.Loading || moves is LoadState.Loading

    val pokemon = (pokemon as? LoadState.Success)?.data
    val items = (items as? LoadState.Success)?.data
    val moves = (moves as? LoadState.Success)?.data

    return copy(
      isLoading = isLoading,
      isError = isError,
      pokemon = pokemon,
      items = items,
      moves = moves
    )
  }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val searchMove: SearchMove,
  private val searchItem: SearchItem,
  private val searchPokemon: SearchPokemon
) : ViewModel() {

  companion object {
    private const val SEARCH_ITEM_LIMIT = 6
  }

  val query = MutableStateFlow("")
  val isSearching = query.map { it.isNotBlank() }

  fun searchUIState() = query.filter { it.isNotBlank() }.flatMapLatest {
    val uiState = SearchUIState(isLoading = true)
    combine(
      searchPokemon(it, SEARCH_ITEM_LIMIT),
      searchItem(it, SEARCH_ITEM_LIMIT),
      searchMove(it, SEARCH_ITEM_LIMIT),
      uiState::process
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), uiState)
  }

  fun search(query: String) {
    viewModelScope.launch {
      this@HomeViewModel.query.emit(query)
    }
  }
}