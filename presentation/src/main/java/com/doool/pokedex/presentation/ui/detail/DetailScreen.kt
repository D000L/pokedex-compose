package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.model.Stat
import com.doool.pokedex.domain.model.Type


@Composable
fun DetailScreen(
  pokemonViewModel: PokemonDetailViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {
  val pokemon by pokemonViewModel.pokemon.collectAsState(null)
  val pokemonSpecies by pokemonViewModel.pokemonSpecies.collectAsState(null)

  pokemon?.let { detail ->
    pokemonSpecies?.let { species ->
      Detail(pokemon = detail, pokemonSpecies = species)
    }
  }
}

@Composable
fun Detail(pokemon: PokemonDetail, pokemonSpecies: PokemonSpecies) {
  Column {
    PokemonInfo(pokemon)
    Description(pokemonSpecies)
    Stats(stats = pokemon.stats)
  }
}

@Composable
fun PokemonInfo(pokemon: PokemonDetail){
  Column(
    Modifier
      .fillMaxWidth()
      .height(120.dp)
      .background(Color.Green)
  ) {
    Text(text = pokemon.name)
    Types(types = pokemon.types)
    Image(
      painter = rememberImagePainter(pokemon.image),
      contentDescription = null
    )
  }
}

@Composable
fun Description(pokemonSpecies: PokemonSpecies){
  Text(text = pokemonSpecies.flavorText.first())
}

@Composable
fun Types(types : List<Type>){
  Row {
    types.forEach {
      Type(it)
    }
  }
}

@Composable
fun Type(type: Type) {
  Box(
    Modifier
      .height(24.dp)
      .background(Color.Blue, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center
  ) {
    Text(text = type.name)
  }
}

@Composable
fun Stats(stats: List<Stat>) {
  Column {
    stats.forEach {
      Stat(it)
    }
  }
}

@Composable
fun Stat(stat: Stat) {
  Column {
    Text(text = stat.name)
    Box(
      Modifier
        .fillMaxWidth()
        .height(16.dp)
        .background(Color.Gray, RoundedCornerShape(8.dp))
    ) {
      val fraction = stat.amount / 255.0f
      Box(
        Modifier
          .fillMaxWidth(fraction)
          .height(16.dp)
          .background(Color.Gray, RoundedCornerShape(8.dp))
      )
    }
  }
}