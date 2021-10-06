package com.doool.pokedex.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.doool.pokedex.domain.model.Info

@Composable
fun TypeList(types: List<Info>) {
  Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
    types.forEach { type ->
      type.name.toPokemonType()?.let { Type(it) }
    }
  }
}

@Composable
fun TypeListWithTitle(modifier : Modifier = Modifier, types: List<Info>) {
  Row(modifier =modifier,horizontalArrangement = Arrangement.spacedBy(6.dp)) {
    types.forEach { type ->
      type.name.toPokemonType()?.let { Type(it, text = type.name) }
    }
  }
}

@Composable
fun Type(type: PokemonType, size : Dp = 26.dp, text: String? = null) {
  val color = colorResource(id = type.colorResId)

  Row(
    Modifier
      .height(size)
      .shadow(4.dp, RoundedCornerShape(size / 2))
      .background(color, RoundedCornerShape(size / 2))
      .padding(horizontal = size / 4),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      modifier = Modifier.size(size / 2),
      imageVector = ImageVector.vectorResource(
        id = type.resId
      ), contentDescription = null, tint = Color.White
    )
    if (text != null) {
      Spacer(modifier = Modifier.width(size / 4))
      Text(text = text, color = Color.White)
    }
  }
}

@Composable
fun Space(width: Dp = 0.dp, height: Dp = 0.dp) {
  Spacer(modifier = Modifier.size(width, height))
}

@Composable
fun ColumnScope.SpaceFill() {
  Spacer(modifier = Modifier.weight(1f))
}

@Composable
fun RowScope.SpaceFill() {
  Spacer(modifier = Modifier.weight(1f))
}