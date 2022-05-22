package com.doool.pokedex.pokemon.feature.info.model

import com.doool.pokedex.domain.model.Move

data class MoveListUIModel(
    val moves: List<Move> = emptyList()
)
