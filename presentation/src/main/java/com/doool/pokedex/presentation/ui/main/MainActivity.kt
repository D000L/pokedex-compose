package com.doool.pokedex.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.doool.pokedex.core.LocalLanguage
import com.doool.pokedex.core.theme.PokedexTheme
import com.doool.pokedex.move.destination.MoveInfoDestination
import com.doool.pokedex.move.destination.MoveListDestination
import com.doool.pokedex.navigation.bottomSheet
import com.doool.pokedex.navigation.composable
import com.doool.pokedex.news.destination.NewsDestination
import com.doool.pokedex.pokemon.destination.PokemonInfoDestination
import com.doool.pokedex.pokemon.destination.PokemonListDestination
import com.doool.pokedex.presentation.ui.home.destination.HomeDestination
import com.doool.pokedex.presentation.utils.goDownload
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.isReady
        }

        setContent {
            PokedexTheme {
                val language by viewModel.language.collectAsState()

                CompositionLocalProvider(LocalLanguage provides language) {
                    Surface(Modifier.fillMaxSize()) {
                        MainNavHost()
                    }
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

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainNavHost() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    CompositionLocalProvider(com.doool.pokedex.navigation.LocalNavController provides navController) {
        ModalBottomSheetLayout(
            sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            bottomSheetNavigator = bottomSheetNavigator
        ) {
            NavHost(navController, HomeDestination.route) {
                navDestinations.forEach(::composable)

                bottomSheet(MoveInfoDestination)
            }
        }
    }
}

private val navDestinations = listOf(
    HomeDestination,
    NewsDestination,
    GamesDestination,
    ItemDestination,
    BerryDestination,
    LocationDestination,
    PokemonInfoDestination,
    PokemonListDestination,
    MoveListDestination
)

object GamesDestination : com.doool.pokedex.navigation.NavDestination() {
    override val route = "Games"
}

object ItemDestination : com.doool.pokedex.navigation.NavDestination() {
    override val route = "Item"
}

object BerryDestination : com.doool.pokedex.navigation.NavDestination() {
    override val route = "Berry"
}

object LocationDestination : com.doool.pokedex.navigation.NavDestination() {
    override val route = "Location"
}

@Composable
fun NotDevelop() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Not Developed")
    }
}
