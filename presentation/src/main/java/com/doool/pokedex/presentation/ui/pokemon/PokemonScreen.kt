package com.doool.pokedex.presentation.ui.pokemon

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.presentation.ui.common.*
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun PokemonScreen(
  pokemonViewModel: PokemonViewModel = hiltViewModel(),
  navigateDetail: (Int) -> Unit
) {

  val pokemonList = pokemonViewModel.pokemonList.collectAsLazyPagingItems()

  Column(Modifier.padding(horizontal = 20.dp)) {
    Space(height = 20.dp)
    Text(
      text = "Pokedex",
      fontSize = 28.sp,
      fontWeight = FontWeight.Bold
    )
    Space(height = 18.dp)
    Row {
      Search(Modifier.weight(1f), pokemonViewModel::search)
      Space(width = 10.dp)
      IconButton(onClick = {}) {
        Icon(Icons.Default.Build, contentDescription = null)
      }
    }
    PokemonList(list = pokemonList, navigateDetail)
  }
}

@Preview
@Composable
fun Filter(){
  FlowRow(mainAxisSpacing = 6.dp, crossAxisSpacing = 6.dp) {
    PokemonType.values().forEach {
      Type(type = it)
    }
  }
}

@Composable
fun Search(modifier : Modifier = Modifier, doSearch: (String) -> Unit) {
  var query by remember { mutableStateOf("") }
  val focus = LocalFocusManager.current

  val searchAndKeyboardHide by rememberUpdatedState {
    doSearch(query)
    focus.clearFocus()
  }

  BasicTextField(
    modifier = modifier
      .height(40.dp)
      .background(color = Color.LightGray.copy(alpha = 0.4f), shape = RoundedCornerShape(20.dp))
      .onPreviewKeyEvent {
        if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
          searchAndKeyboardHide()
          true
        } else false
      },
    value = query,
    onValueChange = {
      query = it
      doSearch(query)
    },
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
    Modifier.padding(horizontal = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(modifier = Modifier.size(24.dp), onClick = onClickSearch) {
      Icon(imageVector = Icons.Default.Search, contentDescription = null)
    }
    Box(
      Modifier
        .padding(horizontal = 10.dp)
        .weight(1f)
    ) {
      if (showHint) Text("Let's go pokemon")
      textField()
    }
    if (!showHint) {
      IconButton(modifier = Modifier.size(28.dp), onClick = onClickClear) {
        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
      }
    }
  }
}

@Composable
fun PokemonList(list: LazyPagingItems<PokemonDetail>, navigateDetail: (Int) -> Unit) {
  LazyColumn(contentPadding = PaddingValues(top = 20.dp)) {
    items(list) {
      it?.let { Pokemon(pokemon = it, onClick = navigateDetail) }
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
fun Pokemon(pokemon: PokemonDetail, onClick: (Int) -> Unit) {
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
        .clickable { onClick(pokemon.id) },
      verticalAlignment = Alignment.CenterVertically
    ) {
      PokemonThumbnail(pokemon.image)
      Column {
        Text(
          text = pokemon.name,
          fontSize = 20.sp,
          color = fontColor
        )
        Space(height = 10.dp)
        TypeList(pokemon.types)
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