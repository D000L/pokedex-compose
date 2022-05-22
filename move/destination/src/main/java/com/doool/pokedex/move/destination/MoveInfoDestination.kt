package com.doool.pokedex.move.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.navigation.NavDestination

const val NAME_PARAM = "query"

object MoveInfoDestination : NavDestination() {

    override val route: String = "move/{$NAME_PARAM}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(NAME_PARAM) { type = NavType.StringType }
    )

    fun getRouteByName(query: String): String {
        return route.replace("{$NAME_PARAM}", query)
    }
}
