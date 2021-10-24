package com.doool.pokedex.presentation.ui.main.home

import androidx.compose.runtime.Composable
import com.doool.pokedex.presentation.ui.main.*
import com.doool.pokedex.presentation.ui.main.move.MoveListDestination
import com.doool.pokedex.presentation.ui.main.news.NewsDestination
import com.doool.pokedex.presentation.ui.main.pokemon.list.PokemonListDestination

object HomeDestination : NavDestination() {
  override val route = "Home"
  override val content: @Composable () -> Unit = {
    val navController = LocalNavController.current

    HomeScreen { menu, query ->
      when (menu) {
        Menu.News -> navController.navigate(NewsDestination.route)
        Menu.Pokemon -> navController.navigate(PokemonListDestination.getRouteWithQuery(query))
        Menu.Games -> navController.navigate(GamesDestination.route)
        Menu.Move -> navController.navigate(MoveListDestination.getRouteWithQuery(query))
        Menu.Item -> navController.navigate(ItemDestination.route)
        Menu.Berry -> navController.navigate(BerryDestination.route)
        Menu.Location -> navController.navigate(LocationDestination.route)
      }
    }
  }
}