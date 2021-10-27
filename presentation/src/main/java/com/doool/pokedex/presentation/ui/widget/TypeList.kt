package com.doool.pokedex.presentation.ui.widget

import androidx.annotation.ColorRes
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.presentation.ui.common.PokemonType
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen

@Composable
fun TypeList(modifier: Modifier = Modifier, types: List<Info>) {
  Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
    types.forEach { type ->
      Type(PokemonType.from(type.name))
    }
  }
}

@Composable
fun TypeListWithTitle(modifier: Modifier = Modifier, types: List<Info>) {
  Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
    types.forEach { type ->
      val pokemonType = PokemonType.from(type.name)
      Type(pokemonType, text = pokemonType.name.capitalizeAndRemoveHyphen())
    }
  }
}

@Composable
fun Type(type: PokemonType, size: Dp = 26.dp, text: String? = null) {
  val color = colorResource(id = type.typeColorResId)

  Row(
    Modifier
      .requiredHeight(size)
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
fun Type(@ColorRes color: Int, size: Dp = 26.dp, fontSize: TextUnit = 14.sp, text: String) {
  val color = colorResource(id = color)

  Row(
    Modifier
      .requiredHeight(size)
      .shadow(4.dp, RoundedCornerShape(size / 2))
      .background(color, RoundedCornerShape(size / 2))
      .padding(horizontal = size / 4),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = text, color = Color.White, fontSize = fontSize)
  }
}