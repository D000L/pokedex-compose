package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.doool.pokedex.domain.model.Effect
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.presentation.ui.main.common.SpaceFill
import com.doool.pokedex.presentation.ui.main.common.toPokemonType
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen

@Composable
fun MoveHeader() {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(text = "Move Name", style = MaterialTheme.typography.body2)
    SpaceFill()
    MoveHeaderItem("Power", 40.dp)
    MoveHeaderItem("Acc", 40.dp)
    MoveHeaderItem("PP", 40.dp)
    MoveHeaderItem("Type", 80.dp)
  }
}

@Composable
private fun MoveHeaderItem(title: String, width: Dp) {
  Text(
    modifier = Modifier.width(width),
    textAlign = TextAlign.Center,
    text = title,
    style = MaterialTheme.typography.body2
  )
}

@Composable
private fun MoveItem(title: String, width: Dp) {
  Text(
    modifier = Modifier.width(width),
    textAlign = TextAlign.Center,
    text = title,
    style = MaterialTheme.typography.body1
  )
}

@Composable
fun Move(move: PokemonMove, onItemClicked: () -> Unit = {}) {
  val type = remember(move.type.name) { move.type.name.toPokemonType() }

  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      modifier = Modifier
        .weight(1f)
        .clickable { onItemClicked() },
      text = move.name.capitalizeAndRemoveHyphen(),
      style = MaterialTheme.typography.body1
    )
    MoveItem(move.power.toString(), 40.dp)
    MoveItem(move.accuracy.toString(), 40.dp)
    MoveItem(move.pp.toString(), 40.dp)
    Box(
      Modifier
        .width(80.dp)
        .shadow(4.dp, CircleShape)
        .background(colorResource(id = type.typeColorResId), CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Text(text = type.name)
    }
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