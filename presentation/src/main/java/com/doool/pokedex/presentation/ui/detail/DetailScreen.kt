package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.presentation.ui.common.StatType
import com.doool.pokedex.presentation.ui.common.TypeListWithTitle
import com.doool.pokedex.presentation.ui.common.toStatType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect


@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailScreen(
  initPokemonId: Int = 1,
  viewModel: PokemonDetailViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {

  val items = (1..500).toList()
  val state = rememberPagerState(pageCount = items.size, initPokemonId - 1)

  HorizontalPager(state = state) {
    val uiState by viewModel.loadPokemon(items[it]).collectAsState(initial = DetailUiState())
    DetailPage(uiState)
  }
}

@Composable
fun DetailPage(uiState: DetailUiState) {
  Column {
    PokemonInfo(uiState.pokemon)
    DetailTabLayout {
      when (it) {
        TabState.Detail -> PokemonDetail(uiState.pokemon, uiState.species)
        TabState.Move -> MoveList(uiState.pokemon.moves)
        TabState.Evolution -> EvolutionList(uiState.evolutionChain)
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
fun MoveList(moves: List<Move>) {
  LazyColumn {
    items(moves) {
      Move(it)
    }
  }
}

@Composable
fun Move(move: Move) {
  Text(text = move.name)
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
  Text(text = pokemonSpecies.flavorText.firstOrNull() ?: "")
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