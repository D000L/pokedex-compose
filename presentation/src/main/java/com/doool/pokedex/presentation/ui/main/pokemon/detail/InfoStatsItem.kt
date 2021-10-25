package com.doool.pokedex.presentation.ui.main.pokemon.detail

import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.model.Stat

data class InfoStatsItem(
  val stats: List<Stat> = emptyList(),
  val damageRelations: List<Damage> = emptyList(),
)