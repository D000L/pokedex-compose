package com.doool.pokedex.presentation.ui.main.pokemon.detail

import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString

data class InfoHeaderItem(
  val id: Int = 0,
  val name: String = "",
  val names: List<LocalizedString> = emptyList(),
  val image: String = "",
  val types: List<Info> = emptyList()
)

