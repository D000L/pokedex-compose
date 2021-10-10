package com.doool.pokedex.presentation.ui.main.common

import com.doool.pokedex.R

enum class MoveCategoryColor(val colorRes: Int){
  Status(R.color.pokemon_gray),
  Physical(R.color.pokemon_red),
  Special(R.color.pokemon_blue),
}


fun String.toMoveCategoryColor() = MoveCategoryColor.values().firstOrNull {
  it.name.lowercase() == this.lowercase()
} ?: MoveCategoryColor.Special