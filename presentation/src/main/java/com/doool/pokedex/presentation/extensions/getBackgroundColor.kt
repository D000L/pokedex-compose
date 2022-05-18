package com.doool.pokedex.presentation.extensions

import com.doool.pokedex.domain.model.Info
import com.doool.core.common.PokemonType

fun List<Info>.getBackgroundColor() =
    firstOrNull()?.name?.let { PokemonType.from(it) }?.backgroundResId
        ?: PokemonType.Bug.backgroundResId
