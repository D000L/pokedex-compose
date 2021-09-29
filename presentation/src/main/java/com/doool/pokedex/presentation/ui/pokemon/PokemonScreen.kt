package com.doool.pokedex.presentation.ui.pokemon

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.presentation.ui.common.TypeList

@Composable
fun PokemonScreen(
  pokemonViewModel: PokemonViewModel = hiltViewModel(),
  navigateDetail: (Int) -> Unit
) {

  val pokemonList by pokemonViewModel.pokemonList.collectAsState(initial = emptyList())

  Column(Modifier.padding(horizontal = 20.dp)) {
    Search(pokemonViewModel::search)
    PokemonList(list = pokemonList, navigateDetail)
  }
}

@Composable
fun Search(doSearch: (String) -> Unit) {
  var query by remember { mutableStateOf("") }
  val focus = LocalFocusManager.current

  val searchAndKeyboardHide by rememberUpdatedState {
    doSearch(query)
    focus.clearFocus()
  }

  BasicTextField(
    modifier = Modifier
      .height(40.dp)
      .fillMaxWidth()
      .border(1.dp, Color.Gray, RoundedCornerShape(26.dp))
      .onPreviewKeyEvent {
        if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
          searchAndKeyboardHide()
          true
        } else false
      },
    value = query,
    onValueChange = { query = it },
    singleLine = true,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions {
      searchAndKeyboardHide()
    },
    decorationBox = {
      SearchLayout(
        showHint = query.isEmpty(),
        onClickSearch = searchAndKeyboardHide,
        onClickClear = {
          query = ""
          searchAndKeyboardHide()
        },
        textField = it
      )
    })
}

@Composable
fun SearchLayout(
  showHint: Boolean,
  onClickSearch: () -> Unit,
  onClickClear: () -> Unit,
  textField: @Composable () -> Unit
) {
  Row(
    Modifier.padding(horizontal = 20.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(Modifier.weight(1f)) {
      if (showHint) Text("Let's go pokemon")
      textField()
    }
    IconButton(onClick = onClickClear) {
      Icon(imageVector = Icons.Default.Clear, contentDescription = null)
    }
    IconButton(onClick = onClickSearch) {
      Icon(imageVector = Icons.Default.Search, contentDescription = null)
    }
  }
}

@Composable
fun PokemonList(list: List<PokemonDetail>, navigateDetail: (Int) -> Unit) {
  LazyColumn() {
    items(list) {
      Pokemon(pokemon = it, onClick = navigateDetail)
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
    listOf()
  ), {})
}

@Composable
fun Pokemon(pokemon: PokemonDetail, onClick: (Int) -> Unit) {
  Box(
    Modifier
      .padding(vertical = 6.dp)
      .fillMaxWidth()
      .height(120.dp)
      .background(color = Color.Green, shape = RoundedCornerShape(8.dp))
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .clickable { onClick(pokemon.id) },
      verticalAlignment = Alignment.CenterVertically
    ) {
      PokemonThumbnail(pokemon.image)
      Column {
        Text(text = pokemon.name)
        TypeList(pokemon.types)
      }
    }
    Text(
      modifier = Modifier.align(Alignment.BottomEnd),
      text = "#%03d".format(pokemon.id),
      fontSize = 52.sp,
      color = Color.White,
      fontStyle = FontStyle.Italic,
      fontWeight = FontWeight.Bold
    )
  }
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