package com.doool.pokedex.presentation.ui.common

import androidx.annotation.ColorRes
import com.doool.pokedex.R

enum class StatType(val text: String, @ColorRes val color: Int) {
  Hp("HP", R.color.hp),
  Attack("Attack", R.color.attack),
  Defense("Defense", R.color.defense),
  SpecialAttack("Sp. Atk", R.color.special_attack),
  SpecialDefense("Sp. Def", R.color.special_defense),
  Speed("Speed", R.color.special_defense);
}

fun String.toStatType() = when (this) {
  "hp" -> StatType.Hp
  "attack" -> StatType.Attack
  "defense" -> StatType.Defense
  "special-attack" -> StatType.SpecialAttack
  "special-defense" -> StatType.SpecialDefense
  "speed" -> StatType.Speed
  else -> null
}