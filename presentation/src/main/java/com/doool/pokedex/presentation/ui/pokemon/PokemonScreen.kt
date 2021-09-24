package com.doool.pokedex.presentation.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.Type

@Composable
fun PokemonScreen(
  pokemonViewModel: PokemonViewModel = hiltViewModel(),
  navigateDetail: (Int) -> Unit
) {

  val pokemonList by pokemonViewModel.pokemonList.collectAsState(initial = emptyList())

  Column(Modifier.padding(horizontal = 20.dp)) {
    SearchUI(pokemonViewModel::search)
    PokemonList(list = pokemonList, navigateDetail)
  }
}

@Composable
fun SearchUI(doSearch: (String) -> Unit) {
  var query by remember { mutableStateOf("") }

  BasicTextField(
    modifier = Modifier
      .height(40.dp)
      .fillMaxWidth()
      .border(1.dp, Color.Gray, RoundedCornerShape(26.dp)),
    value = query,
    onValueChange = { query = it },
    singleLine = true,
    decorationBox = {
      Row(
        Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(Modifier.weight(1f)) {
          if (query.isEmpty()) Text("Let's go pokemon")
          it()
        }
        IconButton(onClick = { query = "" }) {
          Icon(imageVector = Icons.Default.Clear, contentDescription = null)
        }
        IconButton(onClick = { doSearch(query) }) {
          Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
      }
    })
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
        .padding(vertical = 6.dp)
        .background(color = Color.Green, shape = RoundedCornerShape(8.dp)),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = "#%03d".format(pokemon.id))
      PokemonThumbnail(pokemon.image)
      TypeList(pokemon.types)
      Text(text = pokemon.name)
    }
  }
}

@Composable
fun TypeList(types: List<Type>) {
  Row {
    types.forEach { type ->
      val pokemonType = PokemonType.values().find {
        it.name.lowercase() == type.name
      }
      pokemonType?.let { Type(it) }
    }
  }
}

@Composable
fun Type(type: PokemonType) {
  val color = colorResource(id = type.colorResId)

  Icon(
    modifier = Modifier
      .size(24.dp)
      .shadow(4.dp, CircleShape)
      .background(color, CircleShape)
      .padding(6.dp),
    imageVector = ImageVector.vectorResource(
      id = type.resId
    ), contentDescription = null, tint = Color.White
  )
}

@Composable
fun PokemonThumbnail(url: String) {
  Image(
    modifier = Modifier
      .padding(4.dp)
      .size(56.dp),
    painter = rememberImagePainter(url),
    contentDescription = null
  )
}