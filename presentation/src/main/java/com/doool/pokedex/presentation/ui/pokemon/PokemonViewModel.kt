package com.doool.pokedex.presentation.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.usecase.DownloadAllData
import com.doool.pokedex.domain.usecase.GetPokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
  private val getPokemonList: GetPokemonList,
  private val downloadAllData: DownloadAllData
) : ViewModel() {

  val pokemonList = getPokemonList()

  init {
   viewModelScope.launch {
     downloadAllData()
   }
  }
}