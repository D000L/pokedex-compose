package com.doool.pokedex.presentation.ui.main.move

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.presentation.ui.NavDestination
import com.doool.pokedex.presentation.ui.nullableType

const val QUERY_PARAM = "query"

object MoveListDestination : NavDestination() {

  override val route: String = "move?$QUERY_PARAM={$QUERY_PARAM}"
  override val arguments: List<NamedNavArgument> = listOf(
    navArgument(QUERY_PARAM) { nullableType(NavType.StringType) }
  )
  override val content: @Composable () -> Unit = {
    MoveScreen()
  }

  fun getRouteWithQuery(query: String? = null): String {
    return route.replace("{$QUERY_PARAM}", query ?: "")
  }
}