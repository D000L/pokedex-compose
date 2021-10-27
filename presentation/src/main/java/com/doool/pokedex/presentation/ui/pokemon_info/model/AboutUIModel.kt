package com.doool.pokedex.presentation.ui.pokemon_info.model

import com.doool.pokedex.domain.model.Ability
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString

data class AboutUIModel(
  val descriptions: List<LocalizedString>,
  val height: Int, val weight: Int,
  val abilities: List<Ability>,
  val genera: List<LocalizedString>,
  val maleRate: Int,
  val femaleRate: Int,
  val eggGroups: List<Info>,
)
