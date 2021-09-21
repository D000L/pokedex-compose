package com.doool.pokedex.presentation.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.Pokemon

@Composable
fun PokemonScreen(
  pokemonViewModel: PokemonViewModel = hiltViewModel(),
  navigateDetail: (String) -> Unit
) {

  val pokemonList = pokemonViewModel.pokemonList.collectAsLazyPagingItems()

  PokemonList(list = pokemonList, navigateDetail)
}

@Composable
fun PokemonList(list: LazyPagingItems<Pokemon>, navigateDetail: (String) -> Unit) {
  LazyColumn() {
    items(list) {
      it?.let { Pokemon(pokemon = it, onClick = navigateDetail) }
    }
  }
}

@Composable
fun Pokemon(pokemon: Pokemon, onClick: (String) -> Unit) {
  Surface(Modifier.clickable { onClick(pokemon.name) }) {
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