package com.doool.pokedex.presentation.ui.pokemon_info.model

import com.doool.pokedex.domain.model.Move

data class MoveListUIModel(
    val moves: List<Move> = emptyList()
)
