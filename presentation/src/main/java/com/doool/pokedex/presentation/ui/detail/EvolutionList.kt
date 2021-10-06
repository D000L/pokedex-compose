package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.presentation.ui.common.EvolutionType
import com.doool.pokedex.presentation.ui.common.Space

@Composable
fun EvolutionList(chainList: List<PokemonEvolutionChain>) {
  Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
    chainList.forEach {
      Evolution(it)
    }
  }
}

@Composable
fun Evolution(chain: PokemonEvolutionChain) {
  val evolutionType = EvolutionType.values().find { it.text == chain.condition.trigger.name }

  Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
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
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Image(
      modifier = Modifier
        .size(96.dp),
      painter = rememberImagePainter(info.url),
      contentDescription = null
    )
    Space(height = 4.dp)
    Text(text = info.name, fontSize = 16.sp)
  }
}

@Composable
fun LevelEvolution(level: Int) {
  Text(text = "$level Level", fontSize = 16.sp)
}

@Composable
fun ItemEvolution(name: String) {
  Text(text = "$name", fontSize = 16.sp)
}