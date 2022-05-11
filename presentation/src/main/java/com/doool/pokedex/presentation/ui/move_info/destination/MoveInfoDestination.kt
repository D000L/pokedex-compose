package com.doool.pokedex.presentation.ui.move_info.destination

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.presentation.nav.NavDestination
import com.doool.pokedex.presentation.ui.move_info.MoveInfoScreen

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
