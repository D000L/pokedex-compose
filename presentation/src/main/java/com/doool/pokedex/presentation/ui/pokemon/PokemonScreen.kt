package com.doool.pokedex.presentation.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.model.PokemonDetail

@Composable
fun PokemonScreen(pokemonViewModel: PokemonViewModel = viewModel()) {

  val pokemonList = pokemonViewModel.pokemonList.collectAsLazyPagingItems()

  PokemonList(list = pokemonList)
}

@Composable
fun PokemonList(list: LazyPagingItems<Pokemon>) {
  LazyColumn() {
    items(list) {
      it?.let { Pokemon(pokemon = it) }
    }
  }
}

@Composable
fun Pokemon(pokemon: Pokemon) {
  Surface {
    Row {
      Image(
        modifier = Modifier.size(48.dp),
        painter = rememberImagePainter(pokemon.imageUrl),
        contentDescription = null
      )
      Text(text = pokemon.name)
    }
  }
}