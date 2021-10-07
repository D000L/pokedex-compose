package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.model.Stat
import com.doool.pokedex.presentation.ui.common.*
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun Stats(stats: List<Stat>, damageRelations: List<Damage>) {
  Column{
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      stats.forEach { stat ->
        stat.name.toStatType()?.let {
          Stat(it, stat.amount)
        }
      }
    }

    Space(height = 20.dp)
    Text(text = "Damage Relations", fontSize = 18.sp)
    Space(height = 10.dp)

    FlowRow {
      damageRelations.forEach { damage ->
        damage.type.toPokemonType()?.let { DamageType(it, damage.amount) }
      }
    }

    Space(height = 20.dp)
  }
}

@Composable
fun DamageType(type: PokemonType, damage: Float) {
  Row(
    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Type(type = type, size = 30.dp)
    Space(width = 6.dp)
    Text(text = "%.0fx".format(damage), fontSize = 13.sp)
  }
}

@Composable
fun Stat(stat: StatType, amount: Int) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(text = stat.text, fontSize = 14.sp, modifier = Modifier.width(64.dp))
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = amount.toString(),
      fontSize = 14.sp,
      modifier = Modifier.width(28.dp),
      textAlign = TextAlign.End
    )
    Spacer(modifier = Modifier.width(6.dp))
    Box(
      Modifier
        .fillMaxWidth()
        .height(8.dp)
        .background(Color.LightGray, RoundedCornerShape(8.dp))
    ) {
      val fraction = amount / 255.0f
      Box(
        Modifier
          .fillMaxWidth(fraction)
          .height(8.dp)
          .background(colorResource(id = stat.color), RoundedCornerShape(8.dp))
      )
    }
  }
}