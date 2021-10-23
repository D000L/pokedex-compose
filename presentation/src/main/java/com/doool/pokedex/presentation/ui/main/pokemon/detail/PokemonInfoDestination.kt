package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.presentation.ui.main.NavDestination

const val NAME_PARAM = "name"

object PokemonInfoDestination : NavDestination() {

  override val route: String = "pokemon/{$NAME_PARAM}"
  override val arguments: List<NamedNavArgument> = listOf(
    navArgument(NAME_PARAM) { type = NavType.StringType }
  )
  override val content: @Composable () -> Unit = {
    PokemonInfoScreen()
  }

  fun getRouteByName(name: String) = route.replace("{$NAME_PARAM}", name)
}