package com.doool.pokedex.presentation.ui.main.pokemon.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.doool.pokedex.presentation.ui.main.common.SpaceFill
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen

@Composable
fun EvolutionList(
  modifier: Modifier = Modifier,
  chainList: List<PokemonEvolutionChain>,
  onClickPokemon: (String) -> Unit
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    chainList.forEach {
      Evolution(it, onClickPokemon)
    }
  }
}

@Composable
private fun Evolution(chain: PokemonEvolutionChain, onClickPokemon: (String) -> Unit) {
  val evolutionType = EvolutionType.values().find { it.text == chain.condition.trigger.name }

  Row {
    Pokemon(chain.from, onClickPokemon)
    SpaceFill()
    Column(Modifier.align(Alignment.CenterVertically)) {
      when (evolutionType) {
        EvolutionType.LevelUp -> LevelEvolution(chain.condition.minLevel)
        EvolutionType.Item -> ItemEvolution(chain.condition.item?.name ?: "")
      }
    }
    SpaceFill()
    Pokemon(chain.to, onClickPokemon)
  }
}

@Composable
private fun Pokemon(pokemonInfo: Info, onClick: (String) -> Unit) {
  Box(modifier = Modifier.height(120.dp).clickable { onClick(pokemonInfo.name) }, contentAlignment = Alignment.Center) {
    DarkPokeball(size = 96.dp, translateOffset = DpOffset(x = 0.dp, y = -16.dp), rotate = 0f)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        modifier = Modifier
          .size(76.dp),
        painter = rememberImagePainter(pokemonInfo.url),
        contentDescription = null
      )
      Space(height = 4.dp)
      Text(text = pokemonInfo.name.capitalizeAndRemoveHyphen(), style = MaterialTheme.typography.body1)
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