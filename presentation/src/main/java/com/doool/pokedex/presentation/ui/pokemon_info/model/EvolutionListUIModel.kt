package com.doool.pokedex.presentation.ui.pokemon_info.model

import com.doool.pokedex.domain.model.PokemonEvolutionChain

data class EvolutionListUIModel(
  val isInit: Boolean = false,
  val isLoading : Boolean = true,
  val evolutions: List<PokemonEvolutionChain> = emptyList()
)
