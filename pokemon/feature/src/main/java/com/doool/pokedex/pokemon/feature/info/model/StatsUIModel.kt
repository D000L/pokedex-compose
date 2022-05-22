package com.doool.pokedex.pokemon.feature.info.model

import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.model.Stat

data class StatsUIModel(
    val stats: List<Stat> = emptyList(),
    val damageRelations: List<Damage> = emptyList(),
)
