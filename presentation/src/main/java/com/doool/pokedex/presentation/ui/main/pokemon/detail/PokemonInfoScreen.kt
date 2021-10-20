package com.doool.pokedex.presentation.ui.main.pokemon.detail

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.domain.usecase.GetMove
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.ui.main.common.SpaceFill
import com.doool.pokedex.presentation.ui.main.common.TypeListWithTitle
import com.doool.pokedex.presentation.ui.main.common.getBackgroundColor
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.getItemTopOffset
import com.doool.viewpager.ViewPager
import com.doool.viewpager.ViewPagerOrientation
import com.doool.viewpager.ViewPagerState
import com.doool.viewpager.rememberViewPagerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.abs

private val TOOLBAR_HEIGHT = 56.dp
private val THUMBNAIL_VIEWPAGER_HEIGHT = 130.dp
private val TITLE_HEIGHT = 92.dp
private val TAB_HEIGHT = 42.dp

private val HEADER_HEIGHT = TOOLBAR_HEIGHT + THUMBNAIL_VIEWPAGER_HEIGHT + TITLE_HEIGHT + TAB_HEIGHT
private val HEADER_HEIGHT_EXCLUDE_PAGER = HEADER_HEIGHT - THUMBNAIL_VIEWPAGER_HEIGHT

enum class TabState {
  About, Stats, Move, Evolution
}

@Composable
fun PokemonInfoScreen(
  viewModel: PokemonInfoViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {
  val pokemonList by remember { viewModel.pokemonList }.collectAsState(emptyList())

  if (pokemonList.isNotEmpty()) {
    val viewPagerState = rememberViewPagerState(currentPage = viewModel.initIndex)

    PokemonInfoScreen(
      viewPagerState,
      pokemonList,
      viewModel.pokemon,
      viewModel.species,
      viewModel.evolutionChain,
      viewModel.damageRelations,
      viewModel.pokemonImageMap,
      navigateBack
    )

    LaunchedEffect(viewPagerState.currentPage) {
      viewModel.setCurrentPokemon(pokemonList[viewPagerState.currentPage])
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PokemonInfoScreen(
  viewPagerState: ViewPagerState,
  items: List<String>,
  pokemon: StateFlow<PokemonDetail>,
  species: StateFlow<PokemonSpecies>,
  evolutionChain: StateFlow<List<PokemonEvolutionChain>>,
  damageRelations: StateFlow<List<Damage>>,
  pokemonImageMap: Map<String, Flow<String>>,
  navigateBack: () -> Unit
) {
  Log.d("composable update", "DetailScreen $evolutionChain")

  val lazyListState = rememberLazyListState()
  var tabState by remember { mutableStateOf(TabState.About) }

  val density = LocalDensity.current
  val offset by derivedStateOf {
    val topOffset = lazyListState.getItemTopOffset()
    val height = density.run { (topOffset.toDp() - HEADER_HEIGHT_EXCLUDE_PAGER) }
    (height / THUMBNAIL_VIEWPAGER_HEIGHT).coerceIn(0f, 1f)
  }

  val dragged by lazyListState.interactionSource.collectIsDraggedAsState()
  LaunchedEffect(dragged) {
    if (!dragged && offset != 0f && offset != 1f) {
      val direction = if (offset > 0.5f) -offset else offset
      lazyListState.animateScrollBy(direction * density.run { THUMBNAIL_VIEWPAGER_HEIGHT.toPx() })
    }
  }

  BoxWithConstraints {
    Log.d("composable update", "BoxWithConstraints $tabState $offset")
    BodyLayout(
      tabState,
      lazyListState,
      remember { PaddingValues(top = HEADER_HEIGHT + 20.dp, start = 30.dp, end = 30.dp) },
      pokemon,
      species,
      evolutionChain,
      damageRelations
    )
    Header(
      tabState,
      { tabState = it },
      viewPagerState,
      pokemonImageMap,
      items,
      pokemon,
      offset,
      navigateBack
    )
  }
}

@Composable
private fun Header(
  tabState: TabState,
  changeTab: (TabState) -> Unit,
  viewPagerState: ViewPagerState,
  pokemonImageMap: Map<String, Flow<String>>,
  items: List<String>,
  pokemon: StateFlow<PokemonDetail>,
  offset: Float,
  navigateBack: () -> Unit
) {

  val pokemon by pokemon.collectAsState()

  val mainColor by animateColorAsState(targetValue = colorResource(pokemon.getBackgroundColor()))

  Column(modifier = Modifier.background(color = mainColor)) {
    HeaderLayout(
      viewPagerState,
      pokemonImageMap,
      items,
      pokemon,
      offset,
      navigateBack
    )
    TabLayout(tabState) { changeTab(it) }
  }
}

@Composable
private fun HeaderLayout(
  viewPagerState: ViewPagerState,
  pokemonImageMap: Map<String, Flow<String>>,
  items: List<String>,
  pokemon: PokemonDetail,
  offset: Float,
  navigateBack: () -> Unit
) {
  Box {
    val viewPagerHeight = THUMBNAIL_VIEWPAGER_HEIGHT * offset

    DetailAppBar(modifier = Modifier.height(TOOLBAR_HEIGHT), onClickBack = navigateBack)

    if (offset * 3 >= 1f) {
      PokemonImagePager(
        modifier = Modifier
          .padding(top = TOOLBAR_HEIGHT)
          .height(viewPagerHeight)
          .graphicsLayer {
            alpha = (offset * 3).coerceIn(0f, 1f)
          },
        viewPagerState = viewPagerState,
        items = items,
        imageMap = pokemonImageMap
      )
    }

    TitleLayout(
      modifier = Modifier
        .padding(
          top = TOOLBAR_HEIGHT + viewPagerHeight,
          start = 20.dp,
          end = 20.dp
        ),
      pokemon = pokemon
    )
  }
}

@Composable
private fun DetailAppBar(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    IconButton(onClick = onClickBack) {
      Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
    }
    SpaceFill()
    IconButton(onClick = onClickBack) {
      Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
    }
  }
}

@Composable
private fun PokemonImagePager(
  modifier: Modifier = Modifier,
  viewPagerState: ViewPagerState = rememberViewPagerState(),
  items: List<String> = listOf(),
  imageMap: Map<String, Flow<String>>
) {
  Log.d("composable update", "PokemonImagePager")

  BoxWithConstraints(modifier) {
    val (width, offsetY) = LocalDensity.current.run { Pair(maxWidth.toPx(), -5.dp.toPx()) }

    val transformer = remember {
      Transformer(
        mapOf(-2 to -width, -1 to -width / 2f, 0 to 0f, 1 to width / 2f, 2 to width),
        mapOf(-2 to offsetY * 2, -1 to offsetY, 0 to 0f, 1 to offsetY, 2 to offsetY * 2)
      )
    }

    ViewPager(
      state = viewPagerState,
      transformer = transformer,
      orientation = ViewPagerOrientation.Horizontal
    ) {
      items(items) { pokemonName ->
        val pageOffset = (1 - abs(getPagePosition())).coerceIn(0f, 1f)
        val sizePercent = 0.5f + pageOffset / 2f

        val imageUrl by remember { imageMap.getValue(pokemonName) }.collectAsState(initial = "")

        Image(
          modifier = Modifier
            .fillMaxSize(sizePercent)
            .alpha((pageOffset).coerceIn(0.1f, 1f)),
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
}

@Composable
private fun TitleLayout(modifier: Modifier, pokemon: PokemonDetail) {
  Column(modifier.height(TITLE_HEIGHT)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = pokemon.name.capitalizeAndRemoveHyphen(),
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = Color.White
      )
      SpaceFill()
      Text(
        text = "#%03d".format(pokemon.id),
        style = MaterialTheme.typography.h3,
        color = Color.White.copy(alpha = 0.7f)
      )
    }
    Space(height = 6.dp)
    TypeListWithTitle(types = pokemon.types)
  }
}

@Composable
private fun TabLayout(tabState: TabState, changeTab: (TabState) -> Unit) {
  TabRow(
    modifier = Modifier.height(TAB_HEIGHT),
    selectedTabIndex = tabState.ordinal,
    backgroundColor = Color.Transparent,
    contentColor = Color.White
  ) {
    TabState.values().forEach { tab ->
      Tab(selected = tabState == tab, onClick = { changeTab(tab) }) {
        Text(
          text = tab.name,
          color = Color.Black
        )
      }
    }
  }
}

@HiltViewModel
class MoveInfoViewModel @Inject constructor(
  private val getMove: GetMove
) : ViewModel() {

  fun loadPokemonMove(name: String): Flow<PokemonMove> {
    return getMove(name)
  }
}

@Composable
private fun BoxWithConstraintsScope.BodyLayout(
  currentTab: TabState,
  state: LazyListState,
  contentPadding: PaddingValues,
  pokemon: StateFlow<PokemonDetail>,
  species: StateFlow<PokemonSpecies>,
  evolutionChain: StateFlow<List<PokemonEvolutionChain>>,
  damageRelations: StateFlow<List<Damage>>,
  moveViewModel: MoveInfoViewModel = hiltViewModel()
) {
  val pokemon by pokemon.collectAsState()

  val maxHeight =
    LocalDensity.current.run { constraints.maxHeight.toDp() - HEADER_HEIGHT_EXCLUDE_PAGER }
  val defaultModifier = Modifier
    .defaultMinSize(minHeight = maxHeight)
    .fillMaxWidth()

  Log.d("composable update", "BodyLayout $defaultModifier $currentTab")
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    state = state,
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    when (currentTab) {
      TabState.About -> {
        Log.d("composable update", "LazyColumn About")
        item {
          val species by species.collectAsState()

          Info(
            modifier = defaultModifier,
            pokemon = pokemon,
            pokemonSpecies = species
          )
        }
      }
      TabState.Stats -> {
        Log.d("composable update", "LazyColumn Stats")
        item {
          val damageRelations by damageRelations.collectAsState()

          Stats(
            modifier = defaultModifier,
            color = pokemon.getBackgroundColor(),
            stats = pokemon.stats,
            damageRelations = damageRelations
          )
        }
      }
      TabState.Move -> {
        Log.d("composable update", "LazyColumn Move")
        item { Space(height = 10.dp) }
        item { MoveHeader() }
        items(pokemon.moves) {
          val moveDetail by remember(it.name) { moveViewModel.loadPokemonMove(it.name) }.collectAsState(
            initial = PokemonMove()
          )
          Move(moveDetail)
        }
        item { Space(height = 20.dp) }
      }
      TabState.Evolution -> {
        Log.d("composable update", "LazyColumn Evolution")
        item {
          val evolutionChain by evolutionChain.collectAsState()

          EvolutionList(
            modifier = defaultModifier,
            chainList = evolutionChain
          )
        }
      }
    }
  }
}

