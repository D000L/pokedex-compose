package com.doool.pokedex.move.info

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.core.nav.NavDestination

const val NAME_PARAM = "query"

object MoveInfoDestination : NavDestination() {

    override val route: String = "move/{$NAME_PARAM}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(NAME_PARAM) { type = NavType.StringType }
    )
    override val content: @Composable () -> Unit = {
        MoveInfoScreen()
    }

    fun getRouteByName(query: String): String {
        return route.replace("{$NAME_PARAM}", query)
    }
}
