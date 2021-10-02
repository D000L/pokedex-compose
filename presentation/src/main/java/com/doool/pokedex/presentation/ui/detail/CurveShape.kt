package com.doool.pokedex.presentation.ui.detail

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CurveShape : Shape {
  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density
  ): Outline {
    return Outline.Generic(Path().apply {
      val width = size.width
      val height = size.height
      val curveHeight = height * 0.9f

      moveTo(0f, curveHeight)
      quadraticBezierTo(width / 2f, height, width, curveHeight)
      lineTo(width, 0f)
      lineTo(0f, 0f)
      lineTo(0f, height)
      close()
    })
  }
}