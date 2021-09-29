package com.doool.pokedex.presentation.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.usecase.DownloadStaticData
import com.doool.pokedex.domain.usecase.GetPokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
  private val getPokemonList: GetPokemonList,
  private val downloadStaticData: DownloadStaticData
) : ViewModel() {

  private val searchQuery = MutableStateFlow<String?>(null)
  val pokemonList: Flow<List<PokemonDetail>> = searchQuery.flatMapLatest {
    getPokemonList(it)
  }

  init {
    viewModelScope.launch {
      downloadStaticData()
    }
  }

  fun search(query: String) {
    viewModelScope.launch {
      searchQuery.emit(query)
    }
  }
}