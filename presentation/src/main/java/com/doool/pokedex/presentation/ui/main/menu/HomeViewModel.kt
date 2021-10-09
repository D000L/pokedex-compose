package com.doool.pokedex.presentation.ui.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.usecase.SearchPokemonMove
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val searchPokemonMove: SearchPokemonMove) :
  ViewModel() {

  private val query = MutableStateFlow("")
  val isSearching = query.map { it.isNotBlank() }

  val moves = query.flatMapLatest {
    searchPokemonMove(it)
  }

  fun search(query: String) {
    viewModelScope.launch {
      this@HomeViewModel.query.emit(query)
    }
  }
}