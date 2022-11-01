package com.doool.pokedex.pokemon.feature.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.doool.pokedex.core.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.core.utils.localized
import com.doool.pokedex.core.widget.Space
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.getData
import com.doool.pokedex.domain.isLoading
import com.doool.pokedex.pokemon.feature.info.model.AboutUIModel

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
        if (aboutUIState.isLoading())
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun Description(desc: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = desc,
        style = MaterialTheme.typography.body1,
    )
}


@Composable
private fun PokedexData(aboutUIModel: AboutUIModel) {
    CommonSubTitle("Pokedex Data")

    Info(name = "Species", content = aboutUIModel.genera.localized)
    Info(name = "Height", content = "%.1f m".format(aboutUIModel.height / 10f))
    Info(name = "Weight", content = "%.1f kg".format(aboutUIModel.weight / 10f))

    Info(
        name = "Abilities",
        content = aboutUIModel.abilities
            .sortedBy { it.id }
            .fold("") { acc, ability ->
                val localName = ability.names.localized.capitalizeAndRemoveHyphen()
                if (acc.isNotEmpty()) "$acc, $localName"
                else localName
            }
    )
}

@Composable
private fun Breeding(aboutUIModel: AboutUIModel) {
    CommonSubTitle("Breeding")

    val genderRate = if (aboutUIModel.isGenderless) {
        "Genderless"
    } else {
        "♀ %.1f%%, ♂ %.1f%%".format(
            aboutUIModel.maleRate.toFloat(),
            aboutUIModel.femaleRate.toFloat()
        )
    }

    Info(
        name = "Gender",
        content = genderRate
    )
    Info(
        name = "Egg Groups",
        content = aboutUIModel.eggGroups.joinToString(separator = ", ") {
            it.name.capitalizeAndRemoveHyphen()
        }
    )
}

@Composable
private fun Info(name: String, content: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.width(100.dp),
            text = name,
            style = MaterialTheme.typography.body2,
        )
        Text(
            text = content,
            style = MaterialTheme.typography.body1,
        )
    }
}
