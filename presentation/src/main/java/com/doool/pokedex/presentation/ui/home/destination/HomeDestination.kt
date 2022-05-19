package com.doool.pokedex.presentation.ui.home.destination

import androidx.compose.runtime.Composable
import com.doool.core.nav.LocalNavController
import com.doool.core.nav.NavDestination
import com.doool.pokedex.presentation.ui.home.HomeScreen
import com.doool.pokedex.presentation.ui.home.Menu
import com.doool.pokedex.presentation.ui.main.BerryDestination
import com.doool.pokedex.presentation.ui.main.GamesDestination
import com.doool.pokedex.presentation.ui.main.ItemDestination
import com.doool.pokedex.presentation.ui.main.LocationDestination
import com.doool.pokedex.move.list.MoveListDestination
import com.doool.pokedex.presentation.ui.news.destination.NewsDestination
import com.doool.pokedex.pokemon.list.destination.PokemonListDestination

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
