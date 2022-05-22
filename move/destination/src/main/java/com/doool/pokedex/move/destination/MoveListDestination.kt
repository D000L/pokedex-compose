package com.doool.pokedex.move.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.navigation.nullableType

object MoveListDestination : com.doool.pokedex.navigation.NavDestination() {

    override val route: String = "move?$QUERY_PARAM={$QUERY_PARAM}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(QUERY_PARAM) { nullableType(NavType.StringType) }
    )

    fun getRouteWithQuery(query: String? = null): String {
        return route.replace("{$QUERY_PARAM}", query ?: "")
    }
}

const val QUERY_PARAM = "query"
