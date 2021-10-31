package com.doool.pokedex.presentation.ui.pokemon_info

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
import com.doool.pokedex.R
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.presentation.LocalNavController
import com.doool.pokedex.presentation.LocalPokemonColor
import com.doool.pokedex.presentation.extensions.getBackgroundColor
import com.doool.pokedex.presentation.ui.move_info.destination.MoveInfoDestination
import com.doool.pokedex.presentation.ui.pokemon_info.model.HeaderUIModel
import com.doool.pokedex.presentation.ui.widget.*
import com.doool.pokedex.presentation.utils.Process
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.getItemTopOffset
import com.doool.pokedex.presentation.utils.localized
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import kotlin.math.abs

private val THUMBNAIL_VIEWPAGER_HEIGHT = 130.dp
private val TITLE_HEIGHT = 112.dp
private val TAB_HEIGHT = 42.dp

private val HEADER_HEIGHT = TOOLBAR_HEIGHT + THUMBNAIL_VIEWPAGER_HEIGHT + TITLE_HEIGHT + TAB_HEIGHT
private val HEADER_HEIGHT_EXCLUDE_PAGER = HEADER_HEIGHT - THUMBNAIL_VIEWPAGER_HEIGHT

enum class TabState {
  About, Stats, Move, Evolution
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PokemonInfoScreen(
  viewModel: PokemonInfoViewModel = hiltViewModel()
) {
  val pokemonList by viewModel.pokemonList.collectAsState(emptyList())

  if (pokemonList.isNotEmpty()) {
    val pagerState = rememberPagerState(initialPage = viewModel.initIndex)

    PokemonInfo(
      pagerState,
      viewModel,
      pokemonList
    )

    LaunchedEffect(pagerState.currentPage) {
      viewModel.setCurrentPokemon(pokemonList[pagerState.currentPage].name)
    }
  }
}

@ExperimentalPagerApi
@Composable
fun PokemonInfo(
  pagerState: PagerState = rememberPagerState(),
  viewModel: PokemonInfoViewModel,
  items: List<Pokemon>
) {
  val coroutine = rememberCoroutineScope()
  val density = LocalDensity.current

  val lazyListState = rememberLazyListState()
  var tabState by remember { mutableStateOf(TabState.About) }

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
    val minHeight = maxHeight - HEADER_HEIGHT_EXCLUDE_PAGER
    val headerState by viewModel.headerState.collectAsState()

    CompositionLocalProvider(LocalPokemonColor provides colorResource(id = headerState.types.getBackgroundColor())) {
      Body(
        Modifier.defaultMinSize(minHeight = minHeight),
        lazyListState,
        tabState,
        viewModel
      ) { name ->
        coroutine.launch { pagerState.scrollToPage(items.indexOfFirst { it.name == name }) }
      }

      val mainColor by animateColorAsState(targetValue = LocalPokemonColor.current)
      Column(
        modifier = Modifier
          .background(color = mainColor)
          .padding(top = TOOLBAR_HEIGHT)
      ) {
        Header(
          pagerState,
          items,
          headerState,
          offset
        )
        Tab(tabState) { tabState = it }
      }
      DefaultAppBar(tintColor = Color.White, showDivider = false)
    }
  }
}

@Composable
private fun Body(
  modifier: Modifier,
  state: LazyListState = rememberLazyListState(),
  tabState: TabState,
  viewModel: PokemonInfoViewModel,
  onClickPokemon: (String) -> Unit,
) {
  val navController = LocalNavController.current
  val moveItems by viewModel.moveState.collectAsState()

  LazyColumn(
    state = state,
    contentPadding = PaddingValues(top = HEADER_HEIGHT)
  ) {
    item { Space(height = 20.dp) }
    when (tabState) {
      TabState.About -> item {
        val aboutState by viewModel.aboutState.collectAsState()

        aboutState.Process(onComplete = {
          About(
            modifier = modifier.padding(horizontal = 20.dp),
            aboutUIModel = it
          )
        },onLoading = {
          CircularProgressIndicator()
        })
      }
      TabState.Stats -> item {
        val statsState by viewModel.statsState.collectAsState()

        statsState.Process(onComplete = {
          Stats(
            modifier = modifier.padding(horizontal = 20.dp),
            statsUIModel = it,
          )
        })
      }
      TabState.Move -> {
        item { MoveHeader() }
        items(moveItems, key = { it.name }) {
          val move by remember(it.name) { viewModel.loadPokemonMove(it.name) }.collectAsState()
          Move(move) {
            navController.navigate(MoveInfoDestination.getRouteByName(it))
          }
        }
      }
      TabState.Evolution -> item {
        val evolutionChain by viewModel.evolutionChain.collectAsState()

        EvolutionList(
          modifier = modifier.padding(horizontal = 20.dp),
          chainList = evolutionChain,
          onClickPokemon = onClickPokemon
        )
      }
    }
  }
}

@ExperimentalPagerApi
@Composable
private fun Header(
  pagerState: PagerState = rememberPagerState(),
  items: List<Pokemon>,
  headerUIModel: HeaderUIModel,
  offset: Float
) {
  Box {
    val viewPagerHeight = THUMBNAIL_VIEWPAGER_HEIGHT * offset

    if (offset * 3 >= 1f) {
      HorizontalPager(
        modifier = Modifier
          .height(viewPagerHeight)
          .graphicsLayer {
            alpha = (offset * 3).coerceIn(0f, 1f)
          },
        count = items.size,
        key = { items[it].id },
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 100.dp)
      ) { index ->
        val pokemon = items[index]
        PokemonImage(pokemon.imageUrl, calculateCurrentOffsetForPage(index))
      }
    }

    TitleLayout(
      modifier = Modifier
        .padding(
          top = viewPagerHeight,
          start = 20.dp,
          end = 20.dp
        ),
      headerUIModel = headerUIModel
    )
  }
}

@Composable
private fun PokemonImage(imageUrl: String, pageOffset: Float) {
  val pageOffset = (1 - abs(pageOffset)).coerceIn(0f, 1f)
  val sizePercent = 0.5f + pageOffset / 2f

  Image(
    modifier = Modifier
      .fillMaxSize(sizePercent)
      .alpha((pageOffset).coerceIn(0.1f, 1f)),
    colorFilter = ColorFilter.tint(
      Color.Black.copy(alpha = 1f - pageOffset),
      blendMode = BlendMode.SrcAtop
    ),
    painter = rememberImagePainter(imageUrl) {
      placeholder(R.drawable.ic_pokeball)
      error(R.drawable.ic_pokeball)
    },
    contentDescription = null
  )
}

@Composable
private fun TitleLayout(modifier: Modifier, headerUIModel: HeaderUIModel) {
  Column(modifier.height(TITLE_HEIGHT)) {
    Text(
      text = headerUIModel.formNames.localized.capitalizeAndRemoveHyphen(),
      style = MaterialTheme.typography.subtitle1,
      color = Color.White.copy(alpha = 0.5f)
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = headerUIModel.names.localized.capitalizeAndRemoveHyphen(),
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = Color.White
      )
      SpaceFill()
      Text(
        text = "#%03d".format(headerUIModel.id),
        style = MaterialTheme.typography.h3,
        color = Color.White.copy(alpha = 0.7f)
      )
    }
    Space(height = 6.dp)
    TypeListWithTitle(types = headerUIModel.types)
  }
}

@Composable
private fun Tab(tabState: TabState, changeTab: (TabState) -> Unit) {
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