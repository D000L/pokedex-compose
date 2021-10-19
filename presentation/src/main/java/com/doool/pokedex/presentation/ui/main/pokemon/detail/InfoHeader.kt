package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.annotation.ColorRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource

@Composable
fun DetailHeader(title: String, @ColorRes color: Int) {
  Text(text = title, style = MaterialTheme.typography.subtitle2, color = colorResource(id = color))
}
