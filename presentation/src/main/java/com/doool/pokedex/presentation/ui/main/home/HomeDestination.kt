package com.doool.pokedex.presentation.ui.main.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.doool.pokedex.presentation.ui.main.*
import com.doool.pokedex.presentation.ui.main.move.MoveInfoDestination
import com.doool.pokedex.presentation.ui.main.move.MoveListDestination
import com.doool.pokedex.presentation.ui.main.news.NewsDestination
import com.doool.pokedex.presentation.ui.main.pokemon.detail.PokemonInfoDestination
import com.doool.pokedex.presentation.ui.main.pokemon.list.PokemonListDestination

object HomeDestination : NavDestination() {
  override val route = "Home"
  override val content: @Composable () -> Unit = {
    val navController = LocalNavController.current

    HomeScreen(onClickMenu = { menu, query ->
      when (menu) {
        Menu.News -> navController.navigate(NewsDestination.route)
        Menu.Pokemon -> navController.navigate(PokemonListDestination.getRouteWithQuery(query))
        Menu.Games -> navController.navigate(Games.route)
        Menu.Move -> navController.navigate(MoveListDestination.getRouteWithQuery(query))
        Menu.Item -> navController.navigate(Item.route)
        Menu.Berry -> navController.navigate(Berry.route)
        Menu.Location -> navController.navigate(Location.route)
      }
    }, onClickDetail = { menu, item ->
      when (menu) {
        Menu.Pokemon -> navController.navigate(PokemonInfoDestination.getRouteByName(item))
        Menu.Move -> navController.navigate(MoveInfoDestination.getRouteByName(item))
        Menu.Item -> navController.navigate(Item.route)
      }
    })
  }
}