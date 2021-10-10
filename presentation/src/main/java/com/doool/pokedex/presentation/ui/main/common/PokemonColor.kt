package com.doool.pokedex.presentation.ui.main.common

import androidx.annotation.ColorRes
import com.doool.pokedex.R

enum class PokemonColor(@ColorRes val colorRes: Int, @ColorRes val fontColorRes: Int) {
  Red(R.color.pokemon_red, R.color.white),
  Blue(R.color.pokemon_blue, R.color.white),
  Gray(R.color.pokemon_gray, R.color.white),
  Yellow(R.color.pokemon_yellow, R.color.black),
  Green(R.color.pokemon_green, R.color.white),
  Brown(R.color.pokemon_brown, R.color.white),
  Purple(R.color.pokemon_purple, R.color.white),
  White(R.color.pokemon_white, R.color.black),
  Black(R.color.pokemon_black, R.color.white),
  Pink(R.color.pokemon_pink, R.color.white)
}

fun String.toPokemonColor() = PokemonColor.values().firstOrNull {
  it.name.lowercase() == this.lowercase()
} ?: PokemonColor.Red
