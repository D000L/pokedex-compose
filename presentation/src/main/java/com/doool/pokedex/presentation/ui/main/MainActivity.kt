package com.doool.pokedex.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doool.pokedex.presentation.ui.main.home.HomeDestination
import com.doool.pokedex.presentation.ui.main.move.MoveInfoDestination
import com.doool.pokedex.presentation.ui.main.move.MoveListDestination
import com.doool.pokedex.presentation.ui.main.pokemon.detail.PokemonInfoDestination
import com.doool.pokedex.presentation.ui.main.pokemon.list.PokemonListDestination
import com.doool.pokedex.presentation.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      PokedexTheme {
        MainNavHost()
      }
    }
  }
}

@Composable
fun MainNavHost() {
  val navController = rememberNavController()

  NavHost(navController, HomeDestination.route) {
    (mainNav + listOf(
      PokemonInfoDestination,
      PokemonListDestination,
      MoveListDestination,
      MoveInfoDestination
    )).forEach { destination ->
      composable(destination.route, arguments = destination.arguments) {
        destination.content(navController)
      }
    }
  }
}

@Composable
fun NotDevelop() {
  Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Text(text = "Not Developed")
  }
}

val mainNav = listOf(HomeDestination, News, Games, Item, Berry, Location)

object News : NavDestination() {
  override val route = "News"
  override val content: @Composable (NavController) -> Unit = { NotDevelop() }
}

object Games : NavDestination() {
  override val route = "Games"
  override val content: @Composable (NavController) -> Unit = { NotDevelop() }
}

object Item : NavDestination() {
  override val route = "Item"
  override val content: @Composable (NavController) -> Unit = { NotDevelop() }
}

object Berry : NavDestination() {
  override val route = "Berry"
  override val content: @Composable (NavController) -> Unit = { NotDevelop() }
}

object Location : NavDestination() {
  override val route = "Location"
  override val content: @Composable (NavController) -> Unit = { NotDevelop() }
}