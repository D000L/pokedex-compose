package com.doool.pokedex.pokemon.feature.info.model

import com.doool.pokedex.domain.model.PokemonEvolutionChain

data class EvolutionListUIModel(
    val evolutions: List<PokemonEvolutionChain> = emptyList()
)
