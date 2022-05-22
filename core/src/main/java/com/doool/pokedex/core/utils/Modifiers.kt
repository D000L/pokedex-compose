package com.doool.pokedex.core.utils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.ifThen(condition: Boolean, modifier: Modifier): Modifier {
    return if (condition) then(modifier)
    else this
}

fun Modifier.clipBackground(
    color: Color,
    shape: Shape = RectangleShape
) = this
    .background(color, shape)
    .clip(shape)

fun Modifier.defaultPlaceholder(visible: Boolean = true, shape: Shape = RectangleShape) = composed {
    val grayColor = Color.Gray

    this.placeholder(
        visible = visible,
        color = grayColor.copy(0.2f),
        shape = shape,
        highlight = PlaceholderHighlight.shimmer(grayColor.copy(0.5f))
    )
}
