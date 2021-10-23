package com.doool.pokedex.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

fun Modifier.clipBackground(
  color: Color,
  shape: Shape = RectangleShape
) = this
  .background(color, shape)
  .clip(shape)