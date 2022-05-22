package com.doool.pokedex.pokemon.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val NAME_PARAM = "name"

object PokemonInfoDestination : com.doool.pokedex.navigation.NavDestination() {

    override val route: String = "pokemon/{$NAME_PARAM}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(NAME_PARAM) { type = NavType.StringType }
    )

    fun getRouteByName(name: String) = route.replace("{$NAME_PARAM}", name)
}
