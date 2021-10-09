package com.doool.pokedex.presentation.ui.main.menu

import androidx.compose.foundation.Image
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
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
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
      SearchScreen(
        pokemon = remember { viewModel.searchedPokemon() }.collectAsState(initial = LoadState.Loading).value,
        items = remember { viewModel.searchedItems() }.collectAsState(initial = LoadState.Loading).value,
        moves = remember { viewModel.searchedMoves() }.collectAsState(initial = LoadState.Loading).value
      )
    } else {
      MenuScreen(onClickMenu = onClickMenu)
    }
  }
}

@Composable
fun SearchScreen(
  pokemon: LoadState<List<PokemonDetail>>,
  items: LoadState<List<Item>>,
  moves: LoadState<List<PokemonMove>>
) {
  Column(
    Modifier.fillMaxSize()
  ) {

    PokemonThumbnailList(pokemon)
    ItemThumbnailList(items)
    MoveThumbnailList(moves)
  }
}

@Composable
fun PokemonThumbnailList(pokemon: LoadState<List<PokemonDetail>>) {
  Box(Modifier.height(80.dp).fillMaxWidth()) {
    pokemon.Process(onComplete = {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        it.forEach {
          PokemonThumbnail(it)
        }
      }
    }, onLoading = {
      CircularProgressIndicator()
    })
  }
}

@Composable
fun PokemonThumbnail(pokemon: PokemonDetail) {
  if (pokemon.isPlaceholder) {
    Box(
      Modifier
        .size(80.dp)
        .background(Color.Black)
    )
  } else {
    Column(Modifier.size(80.dp)) {
      Image(modifier = Modifier.size(40.dp), painter = rememberImagePainter(pokemon.image), contentDescription = null)
      Text(text = pokemon.name)
    }
  }
}

@Composable
fun ItemThumbnailList(items: LoadState<List<Item>>) {
  Box(Modifier.height(80.dp).fillMaxWidth()) {
    items.Process(onComplete = {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        it.forEach {
          ItemThumbnail(it)
        }
      }
    }, onLoading = {
      CircularProgressIndicator()
    })
  }
}

@Composable
fun ItemThumbnail(item: Item) {
  if (item.isPlaceholder) {
    Box(
      Modifier
        .size(80.dp)
        .background(Color.Black)
    )
  } else {
    Column(Modifier.size(80.dp)) {
      Image(modifier = Modifier.size(40.dp), painter = rememberImagePainter(item.sprites), contentDescription = null)
      Text(text = item.name)
    }
  }
}


@Composable
fun MoveThumbnailList(moves: LoadState<List<PokemonMove>>) {
  Box(Modifier.height(80.dp).fillMaxWidth()) {
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
        .size(80.dp)
        .background(Color.Black)
    )
  } else {
    Box(Modifier.size(80.dp)) {
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