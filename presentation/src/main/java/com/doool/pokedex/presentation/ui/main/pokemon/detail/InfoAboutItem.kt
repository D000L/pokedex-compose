package com.doool.pokedex.presentation.ui.main.pokemon.detail

import com.doool.pokedex.domain.model.Ability
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString

data class InfoAboutItem(
  val descriptions: List<LocalizedString>,
  val height: Int, val weight: Int,
  val abilities: List<Ability>,
  val genera: List<LocalizedString>,
  val maleRate: Int,
  val femaleRate: Int,
  val eggGroups: List<Info>,
)
