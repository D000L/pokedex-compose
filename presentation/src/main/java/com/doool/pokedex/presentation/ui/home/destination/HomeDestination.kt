package com.doool.pokedex.presentation.ui.home.destination

import com.doool.pokedex.navigation.NavDestination

object HomeDestination : NavDestination() {
    override val route = "Home"
//    override val content: @Composable () -> Unit = {
//        val navController = com.doool.pokedex.navigation.LocalNavController.current
//
//        HomeScreen { menu, query ->
//            when (menu) {
//                Menu.News -> navController.navigate(NewsDestination.route)
//                Menu.Pokemon -> navController.navigate(PokemonListDestination.getRouteWithQuery(query))
//                Menu.Games -> navController.navigate(GamesDestination.route)
//                Menu.Move -> navController.navigate(MoveListDestination.getRouteWithQuery(query))
//                Menu.Item -> navController.navigate(ItemDestination.route)
//                Menu.Berry -> navController.navigate(BerryDestination.route)
//                Menu.Location -> navController.navigate(LocationDestination.route)
//            }
//        }
//    }
}
