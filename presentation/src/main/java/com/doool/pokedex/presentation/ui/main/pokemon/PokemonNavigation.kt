package com.doool.pokedex.presentation.ui.main.pokemon

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.doool.pokedex.presentation.ui.main.pokemon.detail.PokemonInfoScreen
import com.doool.pokedex.presentation.ui.main.pokemon.list.PokemonListScreen

private sealed class PokemonNavigation {
  companion object {
    const val root = "pokemon"
  }

  object List : PokemonNavigation() {

    const val queryArgument = "query"
    const val route = "List?$queryArgument={$queryArgument}"

    fun getRouteWithQuery(query: String? = null): String {
      return query?.let {
        route.replace("{$queryArgument}", query)
      } ?: route
    }

    fun arguments() = listOf(
      navArgument(
        queryArgument
      ) {
        this.type = NavType.StringType
        this.nullable = true
        this.defaultValue = null
      }
    )
  }

  object Detail : PokemonNavigation() {

    const val nameArgument = "name"
    const val route = "Detail/{$nameArgument}"

    fun getRouteByName(name: String) = route.replace("{$nameArgument}", name)

    fun arguments() = listOf(
      navArgument(
        nameArgument
      ) {
        this.type = NavType.StringType
      }
    )
  }
}

fun NavGraphBuilder.pokemonNavGraph(
  navController: NavController,
  navActions: PokemonNavActions = PokemonNavActions(navController)
) {
  navigation(startDestination = PokemonNavigation.List.route, route = PokemonNavigation.root) {
    composable(
      route = PokemonNavigation.List.route,
      arguments = PokemonNavigation.List.arguments()
    ) {
      PokemonListScreen(navigateDetail = navActions::navigateInfo)
    }

    composable(
      route = PokemonNavigation.Detail.route,
      arguments = PokemonNavigation.Detail.arguments()
    ) {
      val pokemonName = it.arguments?.getString(PokemonNavigation.Detail.nameArgument) ?: ""
      PokemonInfoScreen(initPokemonName = pokemonName, navigateBack = navController::navigateUp)
    }
  }
}

class PokemonNavActions(private val navController: NavController) {
  fun navigateList(query: String? = null) {
    navController.navigate(PokemonNavigation.List.getRouteWithQuery(query))
  }

  fun navigateInfo(name: String) {
    navController.navigate(PokemonNavigation.Detail.getRouteByName(name))
  }
}