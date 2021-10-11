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
        route.replace("{${queryArgument}}", query)
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

    const val idArgument = "id"
    const val route = "Detail/{$idArgument}"

    fun getRouteWithId(id: Int) = route.replace("{${idArgument}}", id.toString())

    fun arguments() = listOf(
      navArgument(
        idArgument
      ) {
        this.type = NavType.IntType
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
      val pokemonId = it.arguments?.getInt(PokemonNavigation.Detail.idArgument) ?: 1
      PokemonInfoScreen(initPokemonId = pokemonId, navigateBack = navController::navigateUp)
    }
  }
}

class PokemonNavActions(private val navController: NavController) {
  fun navigateList(query: String? = null) {
    navController.navigate(PokemonNavigation.List.getRouteWithQuery(query))
  }

  fun navigateInfo(id: Int) {
    navController.navigate(PokemonNavigation.Detail.getRouteWithId(id))
  }
}