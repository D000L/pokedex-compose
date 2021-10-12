package com.doool.pokedex.presentation.ui.main.move

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.presentation.ui.main.NavDestination
import com.doool.pokedex.presentation.ui.main.nullableType

const val QUERY_PARAM = "query"

object MoveListDestination : NavDestination() {

  override val route: String = "move?$QUERY_PARAM={$QUERY_PARAM}"
  override val arguments: List<NamedNavArgument> = listOf(
    navArgument(QUERY_PARAM) { nullableType(NavType.StringType) }
  )
  override val content: @Composable (NavController) -> Unit = { navController ->
    MoveScreen(navigateInfo = {
      navController.navigate(MoveInfoDestination.getRouteByName(it))
    })
  }

  fun getRouteWithQuery(query: String? = null): String {
    return route.replace("{$QUERY_PARAM}", query ?: "")
  }
}