package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.model.Stat
import com.doool.pokedex.presentation.ui.main.common.*
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun Stats(
  modifier: Modifier = Modifier,
  @ColorRes color: Int,
  stats: List<Stat>,
  damageRelations: List<Damage>
) {
  Column(modifier.padding(vertical = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
    DetailHeader("Base Stats", color)

    stats.forEach { stat ->
      stat.name.toStatType()?.let {
        Stat(it, color, stat.amount)
      }
    }

    Space(height = 10.dp)
    DetailHeader("Type Defenses", color)
    Space(height = 6.dp)

    FlowRow {
      val damageMap = damageRelations.associateBy { it.type }

      PokemonType.values().forEach {
        val damage = damageMap.get(it.name.lowercase())?.amount ?: 1f
        DamageType(it, damage)
      }
    }
  }
}

@Composable
fun DamageType(type: PokemonType, damage: Float) {
  Column(
    modifier = Modifier
      .padding(horizontal = 4.dp, vertical = 2.dp)
      .height(46.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Type(type = type, size = 24.dp)
    Space(height = 4.dp)
    if (damage != 1f) Text(text = getDamageString(damage), style = MaterialTheme.typography.body1)
  }
}

private fun getDamageString(amount: Float): String {
  return when (amount) {
    0f -> "0"
    2f -> "2"
    0.5f -> "½"
    0.25f -> "¼"
    else -> amount.toString()
  }
}

@Composable
fun Stat(stat: StatType, @ColorRes color: Int, amount: Int) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(text = stat.text, style = MaterialTheme.typography.body2, modifier = Modifier.width(54.dp))
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = amount.toString(),
      style = MaterialTheme.typography.body1,
      modifier = Modifier.width(30.dp),
      textAlign = TextAlign.End
    )
    Spacer(modifier = Modifier.width(12.dp))
    Box(
      Modifier
        .fillMaxWidth()
        .height(4.dp)
        .background(Color.LightGray, RoundedCornerShape(8.dp))
    ) {
      val fraction = amount / 255.0f
      Box(
        Modifier
          .fillMaxWidth(fraction)
          .height(4.dp)
          .background(colorResource(color), RoundedCornerShape(8.dp))
      )
    }
  }
}