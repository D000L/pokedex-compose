package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.presentation.ui.main.common.DarkPokeball
import com.doool.pokedex.presentation.ui.main.common.EvolutionType
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen

@Composable
fun EvolutionList(modifier: Modifier = Modifier, chainList: List<PokemonEvolutionChain>) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    chainList.forEach {
      Evolution(it)
    }
  }
}

@Composable
private fun Evolution(chain: PokemonEvolutionChain) {
  val evolutionType = EvolutionType.values().find { it.text == chain.condition.trigger.name }

  Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
    Pokemon(chain.from)
    Column(Modifier.align(Alignment.CenterVertically)) {
      when (evolutionType) {
        EvolutionType.LevelUp -> LevelEvolution(chain.condition.minLevel)
        EvolutionType.Item -> ItemEvolution(chain.condition.item?.name ?: "")
      }
    }
    Pokemon(chain.to)
  }
}

@Composable
private fun Pokemon(info: Info) {
  Box(modifier = Modifier.height(120.dp), contentAlignment = Alignment.Center) {
    DarkPokeball(size = 96.dp, translateOffset = DpOffset(x = 0.dp, y = -16.dp), rotate =0f)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        modifier = Modifier
          .size(76.dp),
        painter = rememberImagePainter(info.url),
        contentDescription = null
      )
      Space(height = 4.dp)
      Text(text = info.name.capitalizeAndRemoveHyphen(), style = MaterialTheme.typography.body1)
    }
  }
}

@Composable
private fun LevelEvolution(level: Int) {
  Text(text = "$level Level", style = MaterialTheme.typography.body2)
}

@Composable
private fun ItemEvolution(name: String) {
  Text(text = name.capitalizeAndRemoveHyphen(), style = MaterialTheme.typography.body2)
}