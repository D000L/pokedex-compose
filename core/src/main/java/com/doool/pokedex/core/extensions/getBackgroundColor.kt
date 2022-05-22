package com.doool.pokedex.core.extensions

import com.doool.pokedex.core.common.PokemonType
import com.doool.pokedex.domain.model.Info

fun List<Info>.getBackgroundColor() =
    firstOrNull()?.name?.let { PokemonType.from(it) }?.backgroundResId
        ?: PokemonType.Bug.backgroundResId
