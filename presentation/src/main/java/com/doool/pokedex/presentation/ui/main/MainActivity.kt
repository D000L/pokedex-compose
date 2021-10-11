package com.doool.pokedex.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doool.pokedex.presentation.ui.main.home.HomeScreen
import com.doool.pokedex.presentation.ui.main.home.Menu
import com.doool.pokedex.presentation.ui.main.news.NewsScreen
import com.doool.pokedex.presentation.ui.main.pokemon.PokemonNavActions
import com.doool.pokedex.presentation.ui.main.pokemon.pokemonNavGraph
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
  val navActions = remember(navController) { MainNavActions(navController) }
  val pokemonNavActions = remember(navController) { PokemonNavActions(navController) }

  NavHost(navController, MainNavigation.Home.route) {
    composable(MainNavigation.Home.route) {
      HomeScreen( onClickMenu = { menu, query ->
        when (menu) {
          Menu.News -> navActions.navigateNews()
          Menu.Pokemon -> pokemonNavActions.navigateList(query)
          Menu.Games -> navActions.navigateGames()
          Menu.Move -> navActions.navigateMove()
          Menu.Item -> navActions.navigateItem()
          Menu.Berry -> navActions.navigateBerry()
          Menu.Location -> navActions.navigateLocation()
        }
      },onClickDetail = { menu, item ->
        when (menu) {
          Menu.Pokemon -> pokemonNavActions.navigateInfo(item)
          Menu.Move -> navActions.navigateMove()
          Menu.Item -> navActions.navigateItem()
        }
      })
    }
    pokemonNavGraph(navController, pokemonNavActions)

    composable(MainNavigation.Games.route) {
      NotDevelop()
    }
    composable(MainNavigation.News.route) {
      NewsScreen()
    }
    composable(MainNavigation.Move.route) {
      NotDevelop()
    }
    composable(MainNavigation.Item.route) {
      NotDevelop()
    }
    composable(MainNavigation.Berry.route) {
      NotDevelop()
    }
    composable(MainNavigation.Location.route) {
      NotDevelop()
    }
  }
}

@Composable
fun NotDevelop() {
  Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Text(text = "Not Developed")
  }
}

sealed class MainNavigation {

  object Home : MainNavigation() {
    const val route = "Home"
  }

  object News : MainNavigation() {
    const val route = "News"
  }

  object Games : MainNavigation() {
    const val route = "Games"
  }

  object Move : MainNavigation() {
    const val route = "Move"
  }

  object Item : MainNavigation() {
    const val route = "Item"
  }

  object Berry : MainNavigation() {
    const val route = "Berry"
  }

  object Location : MainNavigation() {
    const val route = "Location"
  }
}

class MainNavActions(private val navController: NavController) {
  fun navigateGames() {
    navController.navigate(MainNavigation.Games.route)
  }

  fun navigateNews() {
    navController.navigate(MainNavigation.News.route)
  }

  fun navigateMove() {
    navController.navigate(MainNavigation.Move.route)
  }

  fun navigateItem() {
    navController.navigate(MainNavigation.Item.route)
  }

  fun navigateBerry() {
    navController.navigate(MainNavigation.Berry.route)
  }

  fun navigateLocation() {
    navController.navigate(MainNavigation.Location.route)
  }

}