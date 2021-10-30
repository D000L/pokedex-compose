package com.doool.pokedex.presentation.ui.home

import androidx.activity.compose.BackHandler
import androidx.annotation.ColorRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.doool.pokedex.R
import com.doool.pokedex.presentation.LocalNavController
import com.doool.pokedex.presentation.ui.main.GamesDestination
import com.doool.pokedex.presentation.ui.main.ItemDestination
import com.doool.pokedex.presentation.ui.main.LocationDestination
import com.doool.pokedex.presentation.ui.move_list.destination.MoveListDestination
import com.doool.pokedex.presentation.ui.news.destination.NewsDestination
import com.doool.pokedex.presentation.ui.pokemon_list.destination.PokemonListDestination
import com.doool.pokedex.presentation.ui.setting.SettingDropDown
import com.doool.pokedex.presentation.ui.widget.Pokeball
import com.doool.pokedex.presentation.ui.widget.SpaceFill
import com.doool.pokedex.presentation.utils.clipBackground

enum class Menu(@ColorRes val colorRes: Int, val destination: String) {
  News(R.color.background_psychic, NewsDestination.route),
  Pokemon(R.color.background_flying, PokemonListDestination.route),
  Games(R.color.background_rock, GamesDestination.route),
  Move(R.color.background_grass, MoveListDestination.route),
  Item(R.color.background_poison, ItemDestination.route),
  Berry(R.color.background_normal, LocationDestination.route),
  Location(R.color.background_electric, LocationDestination.route),
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
  onClickMenu: (Menu, String?) -> Unit
) {
  var isSearching by rememberSaveable { mutableStateOf(false) }
  val animationOffset by animateFloatAsState(targetValue = if (isSearching) 0f else 1f)

  Box(
    Modifier
      .verticalScroll(rememberScrollState())
      .fillMaxWidth()
  ) {
    Row(
      Modifier
        .padding(start = 20.dp, end = 20.dp, top = 48.dp)
        .alpha(animationOffset)
    ) {
      Text(
        text = stringResource(id = R.string.home_title),
        style = MaterialTheme.typography.h1
      )

      SpaceFill()

      var showMenu by remember { mutableStateOf(false) }
      IconButton(onClick = { showMenu = true }) {
        Icon(Icons.Default.Settings, null)

        SettingDropDown(expended = showMenu) {
          showMenu = false
        }
      }
    }

    MenuScreen(
      Modifier
        .padding(start = 20.dp, end = 20.dp, top = 200.dp)
        .alpha(animationOffset)
    )

    Surface(Modifier.padding(top = 122.dp * animationOffset)) {
      SearchScreen(isSearching, { isSearching = it }, onClickMenu = onClickMenu)
    }
  }

  BackHandler(isSearching) { isSearching = false }
}



@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
  val navController = LocalNavController.current

  Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
    MenuItem(Modifier.fillMaxWidth(), navController, Menu.News)

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(Modifier.weight(1f), navController, Menu.Pokemon)
      MenuItem(Modifier.weight(1f), navController, Menu.Move)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(Modifier.weight(1f), navController, Menu.Item)
      MenuItem(Modifier.weight(1f), navController, Menu.Berry)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(Modifier.weight(1f), navController, Menu.Games)
      MenuItem(Modifier.weight(1f), navController, Menu.Location)
    }
  }
}

@Composable
private fun MenuItem(
  modifier: Modifier = Modifier,
  navController: NavController,
  menu: Menu,
) {
  Box(
    modifier = modifier
      .height(92.dp)
      .clipBackground(colorResource(id = menu.colorRes), shape = RoundedCornerShape(8.dp))
      .clickable {
        navController.navigate(menu.destination)
      },
    contentAlignment = Alignment.Center
  ) {
    Pokeball(120.dp, Alignment.CenterEnd, DpOffset(x = 23.dp, y = -25.dp), rotate = 220f)
    Text(text = menu.name, style = MaterialTheme.typography.h4)
  }
}
