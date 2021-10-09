package com.doool.pokedex.presentation.ui.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.usecase.fetch.FetchItem
import com.doool.pokedex.domain.usecase.fetch.FetchMove
import com.doool.pokedex.domain.usecase.search.SearchItem
import com.doool.pokedex.domain.usecase.search.SearchMove
import com.doool.pokedex.presentation.utils.process
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val searchMove: SearchMove,
  private val searchItem: SearchItem,
  private val fetchMove: FetchMove,
  private val fetchItem: FetchItem
) : ViewModel() {

  private val query = MutableStateFlow("")
  val isSearching = query.map { it.isNotBlank() }

  fun searchedMoves() = query.flatMapLatest {
    searchMove(it)
  }.onEach { data ->
    data.process {
      fetchMoves(it)
    }
  }.flowOn(Dispatchers.IO)

  fun searchedItems() = query.flatMapLatest {
    searchItem(it)
  }.onEach { data ->
    data.process {
      fetchItems(it)
    }
  }.flowOn(Dispatchers.IO)

  fun search(query: String) {
    viewModelScope.launch {
      this@HomeViewModel.query.emit(query)
    }
  }

  private fun fetchMoves(list: List<PokemonMove>) {
    viewModelScope.launch {
      list.forEach {
        if (it.isPlaceholder) fetchMove(it.name)
      }
    }
  }

  private fun fetchItems(list: List<Item>) {
    viewModelScope.launch {
      list.forEach {
        if (it.isPlaceholder) fetchItem(it.name)
      }
    }
  }
}