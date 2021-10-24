package com.doool.pokedex.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doool.pokedex.presentation.ui.main.home.HomeDestination
import com.doool.pokedex.presentation.ui.main.move.MoveInfoDestination
import com.doool.pokedex.presentation.ui.main.move.MoveListDestination
import com.doool.pokedex.presentation.ui.main.news.NewsDestination
import com.doool.pokedex.presentation.ui.main.pokemon.detail.PokemonInfoDestination
import com.doool.pokedex.presentation.ui.main.pokemon.list.PokemonListDestination
import com.doool.pokedex.presentation.ui.theme.PokedexTheme
import com.doool.pokedex.presentation.utils.goDownload
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    installSplashScreen().run {
      setKeepVisibleCondition {
        !viewModel.isReady
      }
    }

    setContent {
      PokedexTheme {
        Surface(Modifier.fillMaxSize()) {
          MainNavHost()
        }
      }
    }

    lifecycleScope.launchWhenResumed {
      viewModel.needDownload.collectLatest {
        goDownload()
      }
    }
  }
}

@Composable
fun MainNavHost() {
  val navController = rememberNavController()

  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(navController, HomeDestination.route) {
      (mainNav + listOf(
        PokemonInfoDestination,
        PokemonListDestination,
        MoveListDestination,
        MoveInfoDestination
      )).forEach { destination ->
        composable(destination.route, arguments = destination.arguments) {
          destination.content()
        }
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

val mainNav = listOf(HomeDestination, NewsDestination, GamesDestination, ItemDestination, BerryDestination, LocationDestination)

object GamesDestination : NavDestination() {
  override val route = "Games"
  override val content: @Composable () -> Unit = { NotDevelop() }
}

object ItemDestination  : NavDestination() {
  override val route = "Item"
  override val content: @Composable () -> Unit = { NotDevelop() }
}

object BerryDestination : NavDestination() {
  override val route = "Berry"
  override val content: @Composable () -> Unit = { NotDevelop() }
}

object LocationDestination : NavDestination() {
  override val route = "Location"
  override val content: @Composable () -> Unit = { NotDevelop() }
}