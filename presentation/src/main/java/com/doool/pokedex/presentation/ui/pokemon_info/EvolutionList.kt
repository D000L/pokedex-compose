package com.doool.pokedex.presentation.ui.pokemon_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.LocalizedInfo
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.presentation.ui.common.EvolutionType
import com.doool.pokedex.presentation.ui.pokemon_info.model.EvolutionListUIModel
import com.doool.pokedex.presentation.ui.widget.DarkPokeball
import com.doool.pokedex.presentation.ui.widget.Space
import com.doool.pokedex.presentation.ui.widget.SpaceFill
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.localized

@Composable
fun EvolutionList(
  modifier: Modifier = Modifier,
  evolutionListUIModel: EvolutionListUIModel,
  onClickPokemon: (String) -> Unit
) {
  Box {
    Column(
      modifier = modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (evolutionListUIModel.isInit) {
        Text(
          modifier = Modifier.padding(top = 140.dp),
          text = "No Evolution",
          style = MaterialTheme.typography.body1
        )
      } else {
        evolutionListUIModel.evolutions.forEach {
          Evolution(it, onClickPokemon)
        }
      }
    }
    if(evolutionListUIModel.isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
        EvolutionType.Trade -> TradeEvolution()
      }
    }
    SpaceFill()
    Pokemon(chain.to, onClickPokemon)
  }
}

@Composable
private fun Pokemon(pokemonInfo: LocalizedInfo, onClick: (String) -> Unit) {
  Box(modifier = Modifier.height(120.dp), contentAlignment = Alignment.Center) {
    DarkPokeball(
      modifier = Modifier.clickable { onClick(pokemonInfo.name) },
      size = 96.dp,
      translateOffset = DpOffset(x = 0.dp, y = -16.dp),
      rotate = 0f
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        modifier = Modifier
          .size(76.dp),
        painter = rememberImagePainter(pokemonInfo.imageUrl),
        contentDescription = null
      )
      Space(height = 4.dp)
      Text(
        text = pokemonInfo.names.localized.capitalizeAndRemoveHyphen(),
        style = MaterialTheme.typography.body1
      )
    }
  }
}

@Composable
private fun LevelEvolution(level: Int) {
  Text(text = "$level Level", style = MaterialTheme.typography.body2)
}

@Composable
private fun TradeEvolution() {
  Text(text = "Trade", style = MaterialTheme.typography.body2)
}

@Composable
private fun ItemEvolution(name: String) {
  Text(text = name.capitalizeAndRemoveHyphen(), style = MaterialTheme.typography.body2)
}