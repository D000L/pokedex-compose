package com.doool.pokedex.presentation.ui.move.list

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.core.nav.NavDestination
import com.doool.core.nav.nullableType

object MoveListDestination : NavDestination() {

    override val route: String = "move?$QUERY_PARAM={$QUERY_PARAM}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(QUERY_PARAM) { nullableType(NavType.StringType) }
    )
    override val content: @Composable () -> Unit = {
        MoveListScreen()
    }

    fun getRouteWithQuery(query: String? = null): String {
        return route.replace("{$QUERY_PARAM}", query ?: "")
    }
}

const val QUERY_PARAM = "query"
