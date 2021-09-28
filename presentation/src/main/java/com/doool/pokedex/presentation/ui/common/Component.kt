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
import androidx.compose.ui.unit.dp
import com.doool.pokedex.domain.model.Info

@Composable
fun TypeList(types: List<Info>) {
  Row {
    types.forEach { type ->
      type.name.toPokemonType()?.let { Type(it) }
    }
  }
}

@Composable
fun TypeListWithTitle(types: List<Info>) {
  Row {
    types.forEach { type ->
      type.name.toPokemonType()?.let { Type(it, type.name) }
    }
  }
}

@Composable
fun Type(type: PokemonType, text: String? = null) {
  val color = colorResource(id = type.colorResId)

  val size = 26.dp

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