package com.doool.pokedex.pokemon.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.navigation.NavDestination
import com.doool.pokedex.navigation.nullableType

const val PARAM_SEARCH_QUERY = "query"

object PokemonListDestination : NavDestination() {
    override val route: String = "pokemon?$PARAM_SEARCH_QUERY={$PARAM_SEARCH_QUERY}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(PARAM_SEARCH_QUERY) { nullableType(NavType.StringType) }
    )

    fun getRouteWithQuery(query: String? = null): String {
        return route.replace("{$PARAM_SEARCH_QUERY}", query ?: "")
    }
}
