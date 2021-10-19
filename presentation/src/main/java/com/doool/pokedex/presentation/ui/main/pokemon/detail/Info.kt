package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.ui.main.common.getBackgroundColor
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen

@Composable
fun Info(modifier: Modifier = Modifier, pokemon: PokemonDetail, pokemonSpecies: PokemonSpecies) {
  Column(
    modifier = modifier.padding(vertical = 20.dp),
    verticalArrangement = Arrangement.spacedBy(15.dp)
  ) {
    Description(pokemonSpecies.flavorText.firstOrNull() ?: "")
    Space(height = 5.dp)

    DetailHeader("Pokedex Data", pokemon.getBackgroundColor())
    InfoItem("Species", pokemonSpecies.genera.genus)
    InfoItem("Height", "%.1f m".format(pokemon.height / 10f))
    InfoItem("Weight", "%.1f kg".format(pokemon.weight / 10f))
    InfoItem(
      "Abilities",
      pokemon.abilities.sortedBy { it.slot }.map { it.ability.name.capitalizeAndRemoveHyphen() }
        .joinToString(
          separator = ", "
        )
    )
    Space(height = 5.dp)
    DetailHeader("Breeding", pokemon.getBackgroundColor())
    InfoItem(
      "Gender",
      "♀ %.1f%%, ♂ %.1f%%".format(
        pokemonSpecies.maleRate.toFloat(),
        pokemonSpecies.femaleRate.toFloat()
      )
    )
    InfoItem(
      "Egg Groups",
      pokemonSpecies.eggGroups.map { it.name.capitalizeAndRemoveHyphen() }.joinToString(
        separator = ", "
      )
    )
  }
}

@Composable
private fun InfoItem(title: String, content: String) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      modifier = Modifier.width(100.dp),
      text = title,
      style = MaterialTheme.typography.body2,
    )
    Text(
      text = content,
      style = MaterialTheme.typography.body1,
    )
  }
}

@Composable
fun Description(desc: String) {
  Text(
    modifier = Modifier.fillMaxWidth(),
    text = desc,
    style = MaterialTheme.typography.body1,
  )
}