package com.doool.pokedex.pokemon.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doool.pokedex.navigation.NavDestination

const val PARAM_POKEMON_NAME = "name"

object PokemonInfoDestination : NavDestination() {

    override val route: String = "pokemon/{$PARAM_POKEMON_NAME}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(PARAM_POKEMON_NAME) { type = NavType.StringType }
    )

    fun getRouteByName(name: String) = route.replace("{$PARAM_POKEMON_NAME}", name)
}
