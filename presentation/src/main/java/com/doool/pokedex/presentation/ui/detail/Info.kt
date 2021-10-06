package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.presentation.ui.common.Space

@Composable
fun Info(pokemon: PokemonDetail, pokemonSpecies: PokemonSpecies) {
  Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Description(pokemonSpecies.flavorText.firstOrNull() ?: "")
    Space(height = 5.dp)
    ColumnItem("Genre", listOf(pokemonSpecies.genera.genus))
    ColumnItem("Height", listOf("%.1f cm".format(pokemon.height / 10f)))
    ColumnItem("Weight", listOf("%.1f kg".format(pokemon.weight / 10f)))
    ColumnItem("Abilities", pokemon.abilities.sortedBy { it.slot }.map { it.ability.name })
  }
}

@Composable
private fun ColumnItem(title: String, content: List<String>) {
  Row {
    Text(
      modifier = Modifier.width(120.dp),
      text = title,
      fontSize = 14.sp,
      color = Color.Black.copy(alpha = 0.7f)
    )
    content.forEach {
      Text(
        text = it,
        fontSize = 14.sp,
        color = Color.Black
      )
      Space(width = 6.dp)
    }
  }
}

@Composable
fun Description(desc: String) {
  Text(
    modifier = Modifier.fillMaxWidth(),
    text = desc,
    fontSize = 16.sp,
    color = Color.Black
  )
}