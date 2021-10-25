package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.localized

@Composable
fun Info(
  modifier: Modifier = Modifier,
  aboutItem: InfoAboutItem
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Description(aboutItem.descriptions.localized)
    Space(height = 12.dp)
    PokedexData(aboutItem)
    Space(height = 12.dp)
    Breeding(aboutItem)
  }
}

@Composable
private fun PokedexData(aboutItem: InfoAboutItem) {
  CommonSubTitle("Pokedex Data")
  InfoItem("Species", aboutItem.genera.localized)
  InfoItem("Height", "%.1f m".format(aboutItem.height / 10f))
  InfoItem("Weight", "%.1f kg".format(aboutItem.weight / 10f))
  InfoItem(
    "Abilities",
    aboutItem.abilities.sortedBy { it.id }.map { it.names.localized.capitalizeAndRemoveHyphen() }
      .joinToString(
        separator = ", "
      )
  )
}

@Composable
private fun Breeding(aboutItem: InfoAboutItem) {
  CommonSubTitle("Breeding")
  InfoItem(
    "Gender",
    "♀ %.1f%%, ♂ %.1f%%".format(
      aboutItem.maleRate.toFloat(),
      aboutItem.femaleRate.toFloat()
    )
  )
  InfoItem(
    "Egg Groups",
    aboutItem.eggGroups.map { it.name.capitalizeAndRemoveHyphen() }.joinToString(
      separator = ", "
    )
  )
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