package com.doool.pokedex.presentation.ui.pokemon_info

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.getData
import com.doool.pokedex.domain.isLoading
import com.doool.pokedex.presentation.ui.pokemon_info.model.AboutUIModel
import com.doool.pokedex.presentation.ui.widget.Space
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.localized

@Composable
fun About(
  modifier: Modifier = Modifier,
  aboutUIState: LoadState<AboutUIModel>
) {
  Box {
    Column(
      modifier = modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      aboutUIState.getData()?.let { aboutUIModel ->
        Description(aboutUIModel.descriptions.localized)
        Space(height = 12.dp)
        PokedexData(aboutUIModel)
        Space(height = 12.dp)
        Breeding(aboutUIModel)
      }

    }
    if (aboutUIState.isLoading()) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
  }
}

@Composable
private fun PokedexData(aboutUIModel: AboutUIModel) {
  CommonSubTitle("Pokedex Data")
  InfoItem("Species", aboutUIModel.genera.localized)
  InfoItem("Height", "%.1f m".format(aboutUIModel.height / 10f))
  InfoItem("Weight", "%.1f kg".format(aboutUIModel.weight / 10f))
  InfoItem(
    "Abilities",
    aboutUIModel.abilities.sortedBy { it.id }.map { it.names.localized.capitalizeAndRemoveHyphen() }
      .joinToString(
        separator = ", "
      )
  )
}

@Composable
private fun Breeding(aboutUIModel: AboutUIModel) {
  CommonSubTitle("Breeding")
  InfoItem(
    "Gender",
    "♀ %.1f%%, ♂ %.1f%%".format(
      aboutUIModel.maleRate.toFloat(),
      aboutUIModel.femaleRate.toFloat()
    )
  )
  InfoItem(
    "Egg Groups",
    aboutUIModel.eggGroups.map { it.name.capitalizeAndRemoveHyphen() }.joinToString(
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