package com.doool.pokedex.presentation.ui.main.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.usecase.LoadState
import com.doool.pokedex.presentation.ui.main.pokemon.Search
import com.doool.pokedex.presentation.utils.Process

enum class Menu {
  Pokemon, Games, Move, Item, Berry, Location
}

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onClickMenu: (Menu) -> Unit) {
  val isSearching by remember { viewModel.isSearching }.collectAsState(initial = false)

  Column {
    Search(doSearch = viewModel::search)

    if (isSearching) {
      SearchScreen(remember { viewModel.moves }.collectAsState(initial = LoadState.Loading).value)
    } else {
      MenuScreen(onClickMenu = onClickMenu)
    }
  }
}

@Composable
fun SearchScreen(moves: LoadState<List<PokemonMove>>) {
  Column(
    Modifier.fillMaxSize()
  ) {
    MoveThumbnailList(moves)
  }
}

@Composable
fun MoveThumbnailList(moves: LoadState<List<PokemonMove>>) {
  Box(Modifier.fillMaxWidth()) {
    moves.Process(onComplete = {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        it.forEach {
          MoveThumbnail(it)
        }
      }
    }, onLoading = {
      CircularProgressIndicator()
    })
  }
}

@Composable
fun MoveThumbnail(move: PokemonMove) {
  if (move.isPlaceholder) {
    Box(
      Modifier
        .size(40.dp)
        .background(Color.Black)
    )
  } else {
    Box(Modifier.size(40.dp)) {
      Text(text = move.name)
    }
  }
}

@Composable
fun MenuScreen(onClickMenu: (Menu) -> Unit) {
  Column {
    Menu.values().forEach {
      MenuItem(it, onClickMenu)
    }
  }
}

@Composable
private fun MenuItem(menu: Menu, onClickMenu: (Menu) -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(40.dp)
      .clickable {
        onClickMenu(menu)
      },
    contentAlignment = Alignment.Center
  ) {
    Text(text = menu.name)
  }
}