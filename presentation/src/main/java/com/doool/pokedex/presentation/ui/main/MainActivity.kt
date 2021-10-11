package com.doool.pokedex.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doool.pokedex.presentation.ui.main.menu.HomeScreen
import com.doool.pokedex.presentation.ui.main.menu.Menu
import com.doool.pokedex.presentation.ui.main.news.NewsScreen
import com.doool.pokedex.presentation.ui.pokemon.PokemonNavActions
import com.doool.pokedex.presentation.ui.pokemon.pokemonNavGraph
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
  val pokemonNavActions = remember(navController) { PokemonNavActions(navController) }

  NavHost(navController, MainNavigation.Home.route) {
    composable(MainNavigation.Home.route) {
      HomeScreen { menu, query ->
        when (menu) {
          Menu.Pokemon -> pokemonNavActions.navigateList(query)
          Menu.Berry -> pokemonNavActions.navigateList(query)
          Menu.Move -> pokemonNavActions.navigateList(query)
        }
      }
    }
    pokemonNavGraph(navController, pokemonNavActions)
    composable(MainNavigation.News.route) {
      NewsScreen()
    }
  }
}

sealed class MainNavigation {

  object Home : MainNavigation() {
    const val route = "Home"
  }

  object News : MainNavigation() {
    const val route = "News"
  }
}