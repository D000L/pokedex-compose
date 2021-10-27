package com.doool.pokedex.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType

object LocalNavController {

  private val LocalNavController = compositionLocalOf<NavHostController?> { null }

  val current: NavHostController
    @Composable get() {
      return LocalNavController.current ?: NavHostController(LocalContext.current)
    }

  infix fun provides(
    navHostController: NavHostController
  ): ProvidedValue<NavHostController?> {
    return LocalNavController.provides(navHostController)
  }
}

abstract class NavDestination {
  abstract val route: String
  open val arguments: List<NamedNavArgument> = emptyList()
  abstract val content: @Composable () -> Unit
}

fun NavArgumentBuilder.nullableType(navType: NavType<*>) {
  this.type = navType
  this.nullable = true
  this.defaultValue = null
}
