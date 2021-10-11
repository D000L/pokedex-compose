package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doool.pokedex.domain.model.Effect
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.ui.main.common.SpaceFill
import com.doool.pokedex.presentation.ui.main.common.Type
import com.doool.pokedex.presentation.ui.main.common.toPokemonType
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen

@Composable
fun Move(move: PokemonMove) {
  Column(
    Modifier
      .background(color = Color.White, shape = RoundedCornerShape(6.dp))
      .padding(horizontal = 6.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = move.name.capitalizeAndRemoveHyphen(),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )
      SpaceFill()
      Item(title = "Power", amount = move.power)
      Space(width = 10.dp)
      Item(title = "Acc", amount = move.accuracy)
      Space(width = 10.dp)
      Item(title = "PP", amount = move.pp)
      Space(width = 10.dp)
      Column {
        move.type.name.toPokemonType()?.let { Type(it.colorResId, size = 18.dp, text = it.name) }
        Text(text = move.damageClass.name.capitalizeAndRemoveHyphen())
      }
    }
    Space(height = 14.dp)
    Text(text = move.effectEntries.shortEffect)
  }
}

@Composable
private fun Item(title: String, amount: Int) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = title, fontSize = 14.sp, color = Color.Black.copy(alpha = 0.8f))
    Text(text = amount.toString(), fontSize = 16.sp)
  }
}

@Composable
@Preview
fun MovePreview() {
  Move(
    PokemonMove(
      name = "Mega Punch",
      id = 1,
      accuracy = 100,
      damageClass = com.doool.pokedex.domain.model.Info(
        name = "physical"
      ),
      flavorTextEntries = listOf("A ramming attack that may cause flinching."),
      effectEntries = Effect(
        effect = "Inflicts regular damage.  Has a 30% chance to make the target flinch.",
        shortEffect = "Has a 30% chance to make the target flinch.",
        effectChance = 30
      ),
      power = 70, pp = 15, type = com.doool.pokedex.domain.model.Info(name = "normal")
    )
  )
}