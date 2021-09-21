package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.presentation.ui.pokemon.PokemonViewModel


@Composable
fun DetailScreen(
  pokemonViewModel: PokemonViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {

  Box() {

  }
}