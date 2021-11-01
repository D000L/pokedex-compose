package com.doool.pokedex.presentation.ui.pokemon_info.model

import com.doool.pokedex.domain.model.Ability
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString

data class AboutUIModel(
  val isLoading: Boolean = true,
  val descriptions: List<LocalizedString> = emptyList(),
  val height: Int = 0, val weight: Int = 0,
  val abilities: List<Ability> = emptyList(),
  val genera: List<LocalizedString> = emptyList(),
  val maleRate: Int = 0,
  val femaleRate: Int = 0,
  val eggGroups: List<Info> = emptyList()
)
