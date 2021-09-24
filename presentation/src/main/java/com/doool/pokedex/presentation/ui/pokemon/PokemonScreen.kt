package com.doool.pokedex.presentation.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonDetail

@Composable
fun PokemonScreen(
  pokemonViewModel: PokemonViewModel = hiltViewModel(),
  navigateDetail: (Int) -> Unit
) {

  val pokemonList by pokemonViewModel.pokemonList.collectAsState(initial = emptyList())

  PokemonList(list = pokemonList, navigateDetail)
}

@Composable
fun PokemonList(list: List<PokemonDetail>, navigateDetail: (Int) -> Unit) {
  LazyColumn() {
    items(list) {
      Pokemon(pokemon = it, onClick = navigateDetail)
    }
  }
}

@Composable
fun Pokemon(pokemon: PokemonDetail, onClick: (Int) -> Unit) {
  Surface(
    Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .clickable { onClick(pokemon.id) }) {
    Row(
      modifier = Modifier
        .padding(horizontal = 20.dp, vertical = 6.dp)
        .background(color = Color.Green, shape = RoundedCornerShape(8.dp)),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = "#%03d".format(pokemon.id))
      PokemonThumbnail(pokemon.image)
      Text(text = pokemon.name)
    }
  }
}

@Composable
fun PokemonThumbnail(url : String){
  Image(
    modifier = Modifier
      .padding(4.dp)
      .size(56.dp),
    painter = rememberImagePainter(url),
    contentDescription = null
  )
}