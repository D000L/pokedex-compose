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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doool.pokedex.core.LocalLanguage
import com.doool.pokedex.core.theme.PokedexTheme
import com.doool.pokedex.move.destination.MoveInfoDestination
import com.doool.pokedex.move.destination.MoveListDestination
import com.doool.pokedex.move.feature.info.MoveInfoScreen
import com.doool.pokedex.move.feature.list.MoveListScreen
import com.doool.pokedex.navigation.NavDestination
import com.doool.pokedex.news.destination.NewsDestination
import com.doool.pokedex.news.feature.NewsScreen
import com.doool.pokedex.pokemon.destination.PokemonInfoDestination
import com.doool.pokedex.pokemon.destination.PokemonListDestination
import com.doool.pokedex.pokemon.destination.goDownload
import com.doool.pokedex.pokemon.feature.info.PokemonInfoScreen
import com.doool.pokedex.pokemon.feature.list.PokemonListScreen
import com.doool.pokedex.presentation.ui.home.HomeScreen
import com.doool.pokedex.presentation.ui.home.Menu
import com.doool.pokedex.presentation.ui.home.destination.HomeDestination
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
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
                navDestinations.forEach { (navDestination, content) ->
                    composable(navDestination.route, arguments = navDestination.arguments) {
                        content()
                    }
                }

                bottomSheet(MoveInfoDestination.route, arguments = MoveInfoDestination.arguments) {
                    MoveInfoScreen()
                }
            }
        }
    }
}

private val navDestinations = listOf<Pair<NavDestination, @Composable () -> Unit>>(
    HomeDestination to {
        val navController = com.doool.pokedex.navigation.LocalNavController.current

        HomeScreen { menu, query ->
            when (menu) {
                Menu.News -> navController.navigate(NewsDestination.route)
                Menu.Pokemon -> navController.navigate(
                    PokemonListDestination.getRouteWithQuery(
                        query
                    )
                )
                Menu.Games -> navController.navigate(GamesDestination.route)
                Menu.Move -> navController.navigate(MoveListDestination.getRouteWithQuery(query))
                Menu.Item -> navController.navigate(ItemDestination.route)
                Menu.Berry -> navController.navigate(BerryDestination.route)
                Menu.Location -> navController.navigate(LocationDestination.route)
            }
        }
    },
    NewsDestination to {
        NewsScreen()
    },
    GamesDestination to {
        NotDevelop()
    },
    ItemDestination to {
        NotDevelop()
    },
    BerryDestination to {
        NotDevelop()
    },
    LocationDestination to {
        NotDevelop()
    },
    PokemonInfoDestination to {
        PokemonInfoScreen()
    },
    PokemonListDestination to {
        PokemonListScreen()
    },
    MoveListDestination to {
        MoveListScreen()
    }
)

object GamesDestination : NavDestination() {
    override val route = "Games"
}

object ItemDestination : NavDestination() {
    override val route = "Item"
}

object BerryDestination : NavDestination() {
    override val route = "Berry"
}

object LocationDestination : NavDestination() {
    override val route = "Location"
}

@Composable
fun NotDevelop() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Not Developed")
    }
}
