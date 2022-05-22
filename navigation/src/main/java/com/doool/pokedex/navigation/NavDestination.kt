package com.doool.pokedex.navigation

import androidx.navigation.NamedNavArgument

abstract class NavDestination {
    abstract val route: String
    open val arguments: List<NamedNavArgument> = emptyList()
}
