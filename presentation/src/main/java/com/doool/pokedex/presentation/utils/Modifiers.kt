package com.doool.pokedex.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.doool.pokedex.R
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.clipBackground(
  color: Color,
  shape: Shape = RectangleShape
) = this
  .background(color, shape)
  .clip(shape)

fun Modifier.defaultPlaceholder(visible : Boolean = true, shape: Shape = RectangleShape) = composed {
  this.placeholder(
    visible,
    color = colorResource(id = R.color.gray).copy(0.2f),
    shape = shape,
    highlight = PlaceholderHighlight.shimmer(colorResource(id = R.color.gray).copy(0.5f))
  )
}