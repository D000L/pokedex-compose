package com.doool.pokedex.pokemon.list.destination

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.core.nav.NavDestination
import com.doool.core.nav.nullableType

const val QUERY_PARAM = "query"

object PokemonListDestination : NavDestination() {
    override val route: String = "pokemon?$QUERY_PARAM={$QUERY_PARAM}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(QUERY_PARAM) { nullableType(NavType.StringType) }
    )
    override val content: @Composable () -> Unit = {
        com.doool.pokedex.pokemon.list.PokemonListScreen()
    }

    fun getRouteWithQuery(query: String? = null): String {
        return route.replace("{$QUERY_PARAM}", query ?: "")
    }
}
