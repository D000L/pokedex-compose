package com.doool.pokedex.move.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.navigation.NavDestination

const val PARAM_MOVE_NAME = "name"

object MoveInfoDestination : NavDestination() {

    override val route: String = "move/{$PARAM_MOVE_NAME}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(PARAM_MOVE_NAME) { type = NavType.StringType }
    )

    fun getRouteByName(query: String): String {
        return route.replace("{$PARAM_MOVE_NAME}", query)
    }
}
