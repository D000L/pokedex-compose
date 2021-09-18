package com.doool.pokedex.presentation.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doool.pokedex.domain.usecase.GetPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val getPokemon: GetPokemon) : ViewModel() {

  init {
    viewModelScope.launch {
      getPokemon(1)
    }
  }
}