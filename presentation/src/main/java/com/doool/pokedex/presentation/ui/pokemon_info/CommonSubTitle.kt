package com.doool.pokedex.presentation.ui.pokemon_info

import androidx.annotation.ColorRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.doool.pokedex.presentation.LocalPokemonColor

@Composable
fun CommonSubTitle(title: String, @ColorRes color: Int? = null) {
  val color = color?.run { colorResource(id = color) } ?: LocalPokemonColor.current
  Text(text = title, style = MaterialTheme.typography.subtitle2, color = color)
}
