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
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.ui.main.common.SpaceFill
import com.doool.pokedex.presentation.ui.main.common.TypeListWithTitle
import com.doool.pokedex.presentation.ui.main.common.toPokemonColor
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.getItemTopOffset
import com.doool.viewpager.ViewPager
import com.doool.viewpager.ViewPagerOrientation
import com.doool.viewpager.ViewPagerState
import com.doool.viewpager.rememberViewPagerState
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs

private val TOOLBAR_HEIGHT = 56.dp
private val THUMBNAIL_VIEWPAGER_HEIGHT = 180.dp
private val TITLE_HEIGHT = 92.dp
private val TAB_HEIGHT = 42.dp

private val HEADER_HEIGHT = TOOLBAR_HEIGHT + THUMBNAIL_VIEWPAGER_HEIGHT + TITLE_HEIGHT + TAB_HEIGHT
private val HEADER_HEIGHT_EXCLUDE_PAGER = HEADER_HEIGHT - THUMBNAIL_VIEWPAGER_HEIGHT

enum class TabState {
  Detail, Stats, Move, Evolution
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PokemonInfoScreen(
  initPokemonName: String,
  viewModel: PokemonInfoViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {
  Log.d("composable update", "DetailScreen")
  val items by viewModel.pokemonList.collectAsState(initial = emptyList())
  val initIndex = remember(items, initPokemonName) { items.indexOf(initPokemonName) }

  if (items.isNotEmpty()) {
    val viewPagerState = rememberViewPagerState(currentPage = initIndex)
    val lazyListState = rememberLazyListState()

    val pokemon by remember { viewModel.getPokemon() }.collectAsState(PokemonDetail())
    var tabState by remember { mutableStateOf(TabState.Detail) }

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
      BodyLayout(
        Modifier.fillMaxSize(),
        tabState,
        lazyListState,
        PaddingValues(top = HEADER_HEIGHT, start = 20.dp, end = 20.dp),
        remember { viewModel.getUiState() }.collectAsState(initial = DetailUiState()).value,
        viewModel::loadPokemonMove
      )
      Column {
        HeaderLayout(viewPagerState, viewModel, items, pokemon, offset, navigateBack)
        TabLayout(tabState) { tabState = it }
      }
    }

    LaunchedEffect(viewPagerState.currentPage) {
      viewModel.setCurrentItem(items[viewPagerState.currentPage])
    }
  }
}

@Composable
private fun HeaderLayout(
  viewPagerState: ViewPagerState,
  viewModel: PokemonInfoViewModel,
  items: List<String>,
  pokemon: PokemonDetail,
  offset: Float,
  navigateBack: () -> Unit
) {
  val mainColor by animateColorAsState(targetValue = colorResource(pokemon.color.name.toPokemonColor().colorRes))

  Box(
    modifier = Modifier.background(color = mainColor)
  ) {
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
        loadImage = viewModel::loadPokemonImage
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
  loadImage: (String) -> Flow<String>
) {
  Log.d("composable update", "PokemonImagePager")
  val loadImage by rememberUpdatedState(newValue = loadImage)

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
      items(items) { pokemonId ->
        val pageOffset = (1 - abs(getPagePosition())).coerceIn(0f, 1f)
        val sizePercent = 0.5f + pageOffset / 2f

        val imageUrl by remember { loadImage(pokemonId) }.collectAsState(initial = "")

        Image(
          modifier = Modifier
            .size(180.dp * sizePercent)
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
        fontSize = 24.sp,
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
    backgroundColor = Color.White,
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

@Composable
private fun BoxWithConstraintsScope.BodyLayout(
  modifier: Modifier = Modifier,
  currentTab: TabState,
  state: LazyListState,
  contentPadding: PaddingValues,
  uiState: DetailUiState,
  loadMove: (String) -> Flow<PokemonMove>
) {
  val loadMove by rememberUpdatedState(newValue = loadMove)

  val maxHeight =
    LocalDensity.current.run { constraints.maxHeight.toDp() - HEADER_HEIGHT_EXCLUDE_PAGER }
  val defaultModifier = Modifier
    .defaultMinSize(minHeight = maxHeight)
    .fillMaxWidth()

  LazyColumn(
    modifier = modifier,
    state = state,
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(26.dp)
  ) {
    when (currentTab) {
      TabState.Detail -> {
        item {
          Info(
            modifier = defaultModifier,
            pokemon = uiState.pokemon,
            pokemonSpecies = uiState.species
          )
        }
      }
      TabState.Stats ->
        item {
          Stats(
            modifier = defaultModifier,
            stats = uiState.pokemon.stats,
            damageRelations = uiState.damageRelations
          )
        }
      TabState.Move -> {
        item { Space(height = 10.dp) }
        items(uiState.pokemon.moves) {
          val moveDetail by remember(it.name) { loadMove(it.name) }.collectAsState(initial = PokemonMove())
          Move(moveDetail)
        }
      }
      TabState.Evolution -> item {
        EvolutionList(
          modifier = defaultModifier,
          chainList = uiState.evolutionChain
        )
      }
    }
  }
}

