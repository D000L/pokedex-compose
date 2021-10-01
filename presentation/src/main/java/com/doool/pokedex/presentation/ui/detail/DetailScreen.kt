package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.presentation.ui.common.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

private val TOOLBAR_HEIGHT = 56.dp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailScreen(
  initPokemonId: Int = 1,
  viewModel: PokemonDetailViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {

  val items = (1..500).toList()
  val state = rememberPagerState(pageCount = items.size, initPokemonId - 1)

  Box {
    HorizontalPager(state = state) {
      val uiState by viewModel.loadPokemon(items[it]).collectAsState(initial = DetailUiState())
      DetailPage(uiState = uiState)
    }
    DetailAppBar(navigateBack)
  }
}

@Composable
fun DetailAppBar(onClickBack: () -> Unit) {
  Row(modifier = Modifier.height(TOOLBAR_HEIGHT)) {
    IconButton(onClick = onClickBack) {
      Icon(Icons.Default.ArrowBack, contentDescription = null)
    }
  }
}

@Composable
fun DetailPage(modifier: Modifier = Modifier, uiState: DetailUiState) {
  Column(modifier.fillMaxSize(1f)) {
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

@Preview
@Composable
fun PreviewPokemonInfo() {
  val pokemon = PokemonDetail(
    height = 5,
    id = 7,
    image = "https://raw.githubuserconten.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwor/7.png",
    name = "squirtle",
    types = listOf(Info("water")),
    color = Info("blue")
  )

  PokemonInfo(pokemon)
}

class CurveShape : Shape {
  override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
    return Outline.Generic(Path().apply {
      val width = size.width
      val height = size.height
      val curveHeight = height * 0.9f

      moveTo(0f, curveHeight)
      quadraticBezierTo(width / 2f, height, width, curveHeight)
      lineTo(width, 0f)
      lineTo(0f, 0f)
      lineTo(0f, height)
      close()
    })
  }
}

@Composable
fun PokemonInfo(pokemon: PokemonDetail) {
  Box {
    Box(modifier = Modifier
      .fillMaxWidth()
      .height(300.dp)
      .background(colorResource(id = pokemon.color.name.toPokemonColor().colorRes), shape = CurveShape()))

    Column(
      Modifier
        .fillMaxWidth()
        .padding(top = TOOLBAR_HEIGHT, start = 20.dp, end = 20.dp)
    ) {
      Row(verticalAlignment = Alignment.Bottom) {
        Text(text = pokemon.name, fontSize = 36.sp, color = Color.White)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "#%03d".format(pokemon.id),
          fontSize = 18.sp,
          color = Color.White.copy(alpha = 0.4f),
          fontWeight = FontWeight.Bold)
      }
      Space(height = 6.dp)
      TypeListWithTitle(pokemon.types)
      Space(height = 20.dp)
      Image(
        modifier = Modifier
          .size(180.dp)
          .align(Alignment.CenterHorizontally),
        painter = rememberImagePainter(pokemon.image),
        contentDescription = null
      )
    }
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