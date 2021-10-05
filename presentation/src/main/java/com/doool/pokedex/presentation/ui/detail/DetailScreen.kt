package com.doool.pokedex.presentation.ui.detail

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.presentation.ui.common.*
import com.doool.viewpager.ViewPager
import com.doool.viewpager.ViewPagerOrientation
import com.doool.viewpager.ViewPagerState
import com.doool.viewpager.rememberViewPagerState
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs

private val TOOLBAR_HEIGHT = 56.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
  initPokemonId: Int = 1,
  viewModel: PokemonDetailViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {
  Log.d("composable update", "DetailScreen")
  val items = (1..500).toList()

  val viewPagerState = rememberViewPagerState(currentPage = initPokemonId - 1)

  LaunchedEffect(viewPagerState.currentPage) {
    viewModel.setCurrentItem(items[viewPagerState.currentPage])
  }

  val headerMin = LocalDensity.current.run { 150.dp.toPx() }
  val headerMax = LocalDensity.current.run { 290.dp.toPx() }

  var scrollY by remember { mutableStateOf(headerMax) }
  val offsetYY = (scrollY - headerMin) / (headerMax - headerMin)

  Column(Modifier.nestedScroll(object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
      val preScrollY = scrollY
      scrollY = (preScrollY + available.y).coerceIn(headerMin, headerMax)
      val used = scrollY - preScrollY
      return super.onPreScroll(Offset(0f, available.y - used), source)
    }
  })) {
    BoxWithConstraints {
      val scrollState = rememberScrollState()

      val pokemon by remember { viewModel.getPokemon() }.collectAsState(PokemonDetail())

      val width = LocalDensity.current.run { maxWidth.toPx() }
      val offsetY = LocalDensity.current.run { -5.dp.toPx() }

      CurveBackground(scrollY, 1f - offsetYY * 0.15f, pokemon.color.name.toPokemonColor().colorRes)

      Column(Modifier.verticalScroll(scrollState)) {
        DetailAppBar(modifier = Modifier.height(TOOLBAR_HEIGHT), onClickBack = navigateBack)
        PokemonInfo(
          modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(start = 20.dp, end = 20.dp),
          pokemon = pokemon
        )
        Space(height = 5.dp)

        PokemonImagePager(
          modifier = Modifier
            .height(180.dp)
            .graphicsLayer {
              alpha = offsetYY
            },
          items = items,
          viewPagerState = viewPagerState,
          width = width,
          offsetY = offsetY
        ) {
          viewModel.loadPokemonImage(it)
        }
      }
    }
    DetailPage(remember { viewModel.getUiState() })
  }
}

@Composable
fun CurveBackground(height: Float, amount: Float, color: Int) {
  Log.d("composable update", "CurveBackground")
  val mainColor by animateColorAsState(targetValue = colorResource(color))

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(LocalDensity.current.run { height.toDp() })
      .background(
        color = mainColor,
        shape = CurveShape(amount)
      )
  )
}

@Composable
fun DetailAppBar(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
  Row(modifier = modifier) {
    IconButton(onClick = onClickBack) {
      Icon(Icons.Default.ArrowBack, contentDescription = null)
    }
  }
}

@Composable
fun PokemonImagePager(
  modifier: Modifier = Modifier,
  items: List<Int> = listOf(),
  viewPagerState: ViewPagerState = rememberViewPagerState(),
  width: Float = 0f,
  offsetY: Float = 0f,
  loadImage: (Int) -> Flow<String>
) {
  Log.d("composable update", "PokemonImagePager")
  val loadImage by rememberUpdatedState(newValue = loadImage)

  ViewPager(
    modifier = modifier,
    state = viewPagerState,
    transformer = Transformer(
      mapOf(-2 to -width, -1 to -width / 2f, 0 to 0f, 1 to width / 2f, 2 to width),
      mapOf(-2 to offsetY * 2, -1 to offsetY, 0 to 0f, 1 to offsetY, 2 to offsetY * 2)
    ),
    orientation = ViewPagerOrientation.Horizontal
  ) {
    items(items) { pokemonId ->
      val pageOffset = (1 - abs(getPagePosition())).coerceIn(0f, 1f)
      val sizePercent = 0.5f + pageOffset / 2f

      val imageUrl by loadImage(pokemonId).collectAsState(initial = "")

      Image(
        modifier = Modifier
          .size(180.dp * sizePercent),
        colorFilter = ColorFilter.tint(
          Color.Black.copy(alpha = 1f - pageOffset),
          blendMode = BlendMode.SrcAtop
        ),
        painter = rememberImagePainter(imageUrl),
        contentDescription = null
      )
    }
  }
}

@Composable
fun PokemonInfo(modifier: Modifier = Modifier, pokemon: PokemonDetail) {
  Log.d("composable update", "PokemonInfo")
  Column(modifier) {
    Row(verticalAlignment = Alignment.Bottom) {
      Text(text = pokemon.name, fontSize = 36.sp, color = Color.White)
      Spacer(modifier = Modifier.weight(1f))
      Text(
        text = "#%03d".format(pokemon.id),
        fontSize = 18.sp,
        color = Color.White.copy(alpha = 0.4f),
        fontWeight = FontWeight.Bold
      )
    }
    Space(height = 6.dp)
    TypeListWithTitle(pokemon.types)
  }
}

@Composable
fun DetailPage(uiState: Flow<DetailUiState>) {
  val uiState by uiState.collectAsState(initial = DetailUiState())

  Log.d("composable update", "DetailPage")
  DetailTabLayout {
    when (it) {
      TabState.Detail -> PokemonDetail(uiState.pokemon, uiState.species)
      TabState.Move -> MoveList(uiState.pokemon.moves)
      TabState.Evolution -> EvolutionList(uiState.evolutionChain)
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
fun PokemonDetail(pokemon: PokemonDetail, pokemonSpecies: PokemonSpecies) {
  Column {
    Description(pokemonSpecies)
    Stats(stats = pokemon.stats)
  }
}

@Composable
fun EvolutionList(chainList: List<PokemonEvolutionChain>) {
  Column {
    chainList.forEach {
      Evolution(it)
    }
  }
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

  PokemonInfo(pokemon = pokemon)
}