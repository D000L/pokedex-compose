package com.doool.pokedex.presentation.ui.main.pokemon.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.presentation.ui.main.common.*
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.flow.Flow

@Composable
fun PokemonListScreen(
  pokemonListViewModel: PokemonListViewModel = hiltViewModel(),
  navigateDetail: (String) -> Unit
) {

  val pokemonList by pokemonListViewModel.pokemonList.collectAsState(initial = emptyList())

  Column(Modifier.padding(horizontal = 20.dp)) {
    Space(height = 20.dp)
    Text(
      text = "Pokedex",
      fontSize = 28.sp,
      fontWeight = FontWeight.Bold
    )
    Space(height = 18.dp)
    PokemonList(list = pokemonList, pokemonListViewModel::getPokemon, navigateDetail)
  }
}

@Preview
@Composable
fun Filter() {
  FlowRow(mainAxisSpacing = 6.dp, crossAxisSpacing = 6.dp) {
    PokemonType.values().forEach {
      Type(type = it)
    }
  }
}

@Composable
fun PokemonList(
  list: List<String>,
  getPokemon: (String) -> Flow<PokemonDetail>,
  navigateDetail: (String) -> Unit
) {
  LazyColumn(contentPadding = PaddingValues(top = 20.dp)) {
    items(list) {
      val pokemon by remember { getPokemon(it) }.collectAsState(initial = PokemonDetail().apply {
        isPlaceholder = true
      })
      Pokemon(pokemon = pokemon, onClick = navigateDetail)
    }
  }
}

@Preview
@Composable
fun PokemonPreview() {
  Pokemon(PokemonDetail(
    101,
    "electrode",
    14,
    54,
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/101.png",
    listOf(),
    listOf(Info("bug"), Info("fairy")),
    listOf(),
    Info("Red")
  ), {})
}

@Composable
fun Pokemon(pokemon: PokemonDetail, onClick: (String) -> Unit) {
  Box(
    Modifier
      .padding(vertical = 6.dp)
      .fillMaxWidth()
      .height(96.dp)
      .shadow(4.dp, RoundedCornerShape(16.dp))
      .background(
        color = colorResource(id = pokemon.color.name.toPokemonColor().colorRes),
        shape = RoundedCornerShape(16.dp)
      )
  ) {
    val fontColor = colorResource(id = pokemon.color.name.toPokemonColor().fontColorRes)

    Row(
      modifier = Modifier
        .fillMaxSize()
        .clickable { onClick(pokemon.name) },
      verticalAlignment = Alignment.CenterVertically
    ) {
      PokemonThumbnail(pokemon.image)
      Column {
        Text(
          text = pokemon.name.capitalizeAndRemoveHyphen(),
          fontSize = 20.sp,
          color = fontColor
        )
        Space(height = 10.dp)
        TypeList(types = pokemon.types)
      }
    }
    Text(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(end = 10.dp),
      text = "#%03d".format(pokemon.id),
      fontSize = 52.sp,
      color = fontColor.copy(alpha = 0.4f),
      fontWeight = FontWeight.Bold
    )
  }
}

@Composable
fun PokemonThumbnail(url: String) {
  Image(
    modifier = Modifier
      .padding(10.dp)
      .size(76.dp),
    painter = rememberImagePainter(url),
    contentDescription = null
  )
}