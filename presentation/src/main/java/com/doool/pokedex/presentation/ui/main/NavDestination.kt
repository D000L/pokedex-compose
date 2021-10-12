package com.doool.pokedex.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavController
import androidx.navigation.NavType

abstract class NavDestination() {
  abstract val route: String
  open val arguments: List<NamedNavArgument> = emptyList()
  abstract val content: @Composable (NavController) -> Unit
}

fun NavArgumentBuilder.nullableType(navType: NavType<*>) {
  this.type = navType
  this.nullable = true
  this.defaultValue = null
}
