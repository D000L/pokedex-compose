package com.doool.pokedex.presentation.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonDetail

@Composable
fun PokemonScreen(pokemonViewModel: PokemonViewModel = viewModel()) {

  val pokemonList by pokemonViewModel.pokemonList.collectAsState(initial = emptyList())

  PokemonList(list = pokemonList)
}

@Composable
fun PokemonList(list: List<PokemonDetail>) {
  LazyColumn() {
    items(list) {
      Pokemon(pokemon = it)
    }
  }
}

@Composable
fun Pokemon(pokemon: PokemonDetail) {
  Surface {
    Row {
      Image(modifier = Modifier.size(48.dp),painter = rememberImagePainter(pokemon.image), contentDescription = null)
      Text(text = pokemon.name)
    }
  }
}