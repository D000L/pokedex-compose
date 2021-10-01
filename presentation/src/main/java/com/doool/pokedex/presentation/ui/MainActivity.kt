package com.doool.pokedex.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.doool.pokedex.presentation.ui.detail.DetailScreen
import com.doool.pokedex.presentation.ui.detail.PokemonDetailViewModel
import com.doool.pokedex.presentation.ui.pokemon.PokemonScreen
import com.doool.pokedex.presentation.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      PokedexTheme {
        App()
      }
    }
  }
}

enum class NavDestination(val argument : Pair<NavType<*>, String>? = null) {
  List, Detail(Pair(NavType.IntType,"POKEMON_ID"))
}

@Composable
fun App() {
  val navController = rememberNavController()
  val navActions = remember(navController) { NavActions(navController) }

  NavHost(navController, NavDestination.List.name) {
    composable(NavDestination.List.name) {
      PokemonScreen(navigateDetail = navActions::navigateDetail)
    }
    composable(
      route = "${NavDestination.Detail.name}/{${NavDestination.Detail.argument!!.second}}",
      arguments = listOf(navArgument(NavDestination.Detail.argument!!.second) {
        type = NavDestination.Detail.argument!!.first
      })
    ) {
      val pokemonId = it.arguments?.getInt(NavDestination.Detail.argument!!.second) ?: 1
      DetailScreen(initPokemonId = pokemonId, navigateBack = navActions::navigateBack)
    }
  }
}

class NavActions(private val navController: NavController) {
  fun navigateBack() {
    navController.navigateUp()
  }

  fun navigateDetail(id: Int) {
    navController.navigate("${NavDestination.Detail.name}/$id")
  }
}