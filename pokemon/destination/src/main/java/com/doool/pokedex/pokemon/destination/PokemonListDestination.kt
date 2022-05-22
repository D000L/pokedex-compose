package com.doool.pokedex.pokemon.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.navigation.nullableType

const val QUERY_PARAM = "query"

object PokemonListDestination : com.doool.pokedex.navigation.NavDestination() {
    override val route: String = "pokemon?$QUERY_PARAM={$QUERY_PARAM}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(QUERY_PARAM) { nullableType(NavType.StringType) }
    )

    fun getRouteWithQuery(query: String? = null): String {
        return route.replace("{$QUERY_PARAM}", query ?: "")
    }
}
