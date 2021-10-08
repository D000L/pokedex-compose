package com.doool.pokedex.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.doool.pokedex.presentation.ui.detail.DetailScreen
import com.doool.pokedex.presentation.ui.download.DownloadScreen
import com.doool.pokedex.presentation.ui.pokemon.PokemonScreen
import com.doool.pokedex.presentation.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val content: View = findViewById(android.R.id.content)
    content.viewTreeObserver.addOnPreDrawListener(
      object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
          // Check if the initial data is ready.
          return if (viewModel.isReady) {
            // The content is ready; start drawing.
            content.viewTreeObserver.removeOnPreDrawListener(this)
            true
          } else {
            // The content is not ready; suspend.
            false
          }
        }
      }
    )

    setContent {
      PokedexTheme {
        val isDownloaded by viewModel.isDownloaded.collectAsState(initial = false)
        val initPage = if (isDownloaded) NavDestination.List else NavDestination.DownLoad
        if(viewModel.isReady) {
          App(initPage)
        }
      }
    }
  }
}

enum class NavDestination(val argument: Pair<NavType<*>, String>? = null) {
  DownLoad, List, Detail(Pair(NavType.IntType, "POKEMON_ID"))
}

@Composable
fun App(initPage: NavDestination) {
  val navController = rememberNavController()
  val navActions = remember(navController) { NavActions(navController) }

  NavHost(navController, initPage.name) {
    composable(NavDestination.DownLoad.name) {
      DownloadScreen(completeDownload = navActions::navigateList)
    }
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

  fun navigateList() {
    navController.navigate(NavDestination.List.name)
  }

  fun navigateDetail(id: Int) {
    navController.navigate("${NavDestination.Detail.name}/$id")
  }
}