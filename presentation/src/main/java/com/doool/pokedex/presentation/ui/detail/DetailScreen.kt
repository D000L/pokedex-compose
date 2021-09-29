package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.model.Stat
import com.doool.pokedex.presentation.ui.common.StatType
import com.doool.pokedex.presentation.ui.common.TypeListWithTitle
import com.doool.pokedex.presentation.ui.common.toStatType


@Composable
fun DetailScreen(
  pokemonViewModel: PokemonDetailViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {
  val pokemon by pokemonViewModel.pokemon.collectAsState(null)
  val pokemonSpecies by pokemonViewModel.pokemonSpecies.collectAsState(null)
  val pokemonEvolutionChain by pokemonViewModel.pokemonEvolutionChain.collectAsState(emptyList())

  pokemon?.let { detail ->
    pokemonSpecies?.let { species ->
      Column {
        PokemonInfo(detail)
        DetailTabLayout {
          when (it) {
            TabState.Detail -> PokemonDetail(detail, species)
            TabState.Move -> NotDevelop()
            TabState.Evolution -> EvolutionList(pokemonEvolutionChain)
          }
        }
      }
    }
  }
}

enum class TabState {
  Detail, Move, Evolution
}

@Composable
fun DetailTabLayout(content: @Composable (TabState) -> Unit) {
  var tabState by remember { mutableStateOf(TabState.Detail) }

  Column(modifier = Modifier.padding(horizontal = 20.dp)) {
    TabRow(
      modifier = Modifier
        .height(42.dp)
        .fillMaxSize(1f)
        .shadow(4.dp, CircleShape)
        .background(Color.White, CircleShape),
      selectedTabIndex = tabState.ordinal,
      backgroundColor = Color.White,
      contentColor = Color.White,
      indicator = {}
    ) {
      TabState.values().forEach { tab ->
        Box(
          modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(1f)
            .background(if (tab == tabState) Color.Blue else Color.White, CircleShape)
            .clickable {
              tabState = tab
            },
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = tab.name,
            color = if (tab == tabState) Color.White else Color.Black
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    content(tabState)
  }
}

@Composable
fun ColumnScope.PokemonDetail(pokemon: PokemonDetail, pokemonSpecies: PokemonSpecies) {
  Description(pokemonSpecies)
  Stats(stats = pokemon.stats)
}

@Composable
fun ColumnScope.EvolutionList(chainList: List<PokemonEvolutionChain>) {
  chainList.forEach {
    Evolution(it)
  }
}

enum class EvolutionType(val text: String) {
  LevelUp("level-up"), Trade("trade"), Item("use-item"), Shed("shed"), Other("other")
}

@Composable
fun Evolution(chain: PokemonEvolutionChain) {
  val evolutionType = EvolutionType.values().find { it.text == chain.condition.trigger.name }

  Row {
    Image(
      painter = rememberImagePainter(chain.from.url),
      contentDescription = null
    )
    when (evolutionType) {
      EvolutionType.LevelUp -> LevelEvolution(chain.condition.minLevel)
      EvolutionType.Item -> ItemEvolution(chain.condition.item?.name ?: "")
    }
    Image(
      painter = rememberImagePainter(chain.to.url),
      contentDescription = null
    )
  }
}

@Composable
fun LevelEvolution(level: Int) {
  Text(text = "$level Level")
}

@Composable
fun ItemEvolution(name: String) {
  Text(text = "$name")
}

@Composable
fun ColumnScope.NotDevelop() {
  Text(text = "Didn't Develop")
}

@Composable
fun PokemonInfo(pokemon: PokemonDetail) {
  Column(
    Modifier
      .fillMaxWidth()
      .height(120.dp)
      .background(Color.Green)
  ) {
    Text(text = pokemon.name)
    TypeListWithTitle(pokemon.types)
    Image(
      painter = rememberImagePainter(pokemon.image),
      contentDescription = null
    )
  }
}

@Composable
fun Description(pokemonSpecies: PokemonSpecies) {
  Text(text = pokemonSpecies.flavorText.first())
}

@Composable
fun Stats(stats: List<Stat>) {
  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    stats.forEach { stat ->
      stat.name.toStatType()?.let {
        Stat(it, stat.amount)
      }
    }
  }
}

@Composable
fun Stat(stat: StatType, amount: Int) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(text = stat.text, modifier = Modifier.width(64.dp))
    Spacer(modifier = Modifier.width(6.dp))
    Text(text = amount.toString(), modifier = Modifier.width(28.dp), textAlign = TextAlign.End)
    Spacer(modifier = Modifier.width(6.dp))
    Box(
      Modifier
        .fillMaxWidth()
        .height(8.dp)
        .background(Color.LightGray, RoundedCornerShape(8.dp))
    ) {
      val fraction = amount / 255.0f
      Box(
        Modifier
          .fillMaxWidth(fraction)
          .height(8.dp)
          .background(colorResource(id = stat.color), RoundedCornerShape(8.dp))
      )
    }
  }
}