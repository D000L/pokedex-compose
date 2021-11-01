package com.doool.pokedex.presentation.ui.pokemon_info.model

import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString

data class HeaderUIModel(
  val isLoading: Boolean = true,
  val id: Int = 0,
  val name: String = "",
  val names: List<LocalizedString> = emptyList(),
  val types: List<Info> = emptyList(),
  val formNames: List<LocalizedString> = emptyList()
)

