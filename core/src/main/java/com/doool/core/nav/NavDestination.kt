package com.doool.core.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument

abstract class NavDestination {
    abstract val route: String
    open val arguments: List<NamedNavArgument> = emptyList()
    abstract val content: @Composable () -> Unit
}
