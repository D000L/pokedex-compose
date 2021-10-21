package com.doool.pokedex.presentation.ui.main.pokemon.detail

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.doool.pokedex.R
import com.doool.pokedex.domain.model.PokemonDetail
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
import kotlinx.coroutines.flow.Flow
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

    PokemonInfo(
      viewPagerState,
      viewModel,
      pokemonList,
      navigateBack
    )

    LaunchedEffect(viewPagerState.currentPage) {
      viewModel.setCurrentPokemon(pokemonList[viewPagerState.currentPage])
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonInfo(
  viewPagerState: ViewPagerState,
  viewModel: PokemonInfoViewModel,
  items: List<String>,
  navigateBack: () -> Unit
) {
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
    val pokemon by viewModel.pokemon.collectAsState()

    CompositionLocalProvider(LocalPokemonColor provides colorResource(id = pokemon.getBackgroundColor())) {
      Body(Modifier.defaultMinSize(minHeight = minHeight), lazyListState, tabState, viewModel)

      val mainColor by animateColorAsState(targetValue = LocalPokemonColor.current)
      Column(
        modifier = Modifier
          .background(color = mainColor)
          .padding(top = TOOLBAR_HEIGHT)
      ) {
        Header(
          viewPagerState,
          viewModel.pokemonImageMap,
          items,
          pokemon,
          offset
        )
        Tab(tabState) { tabState = it }
      }
      AppBar(modifier = Modifier.height(TOOLBAR_HEIGHT), onClickBack = navigateBack)
    }
  }
}

@Composable
private fun Body(
  modifier: Modifier,
  state: LazyListState = rememberLazyListState(),
  tabState: TabState,
  viewModel: PokemonInfoViewModel
) {
  val pokemon by viewModel.pokemon.collectAsState()

  LazyColumn(
    state = state,
    contentPadding = PaddingValues(top = HEADER_HEIGHT + 20.dp, start = 30.dp, end = 30.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    when (tabState) {
      TabState.About -> item {
        val species by viewModel.species.collectAsState()

        Info(
          modifier = modifier,
          pokemon = pokemon,
          pokemonSpecies = species
        )
      }
      TabState.Stats -> item {
        val damageRelations by viewModel.damageRelations.collectAsState()

        Stats(
          modifier = modifier,
          stats = pokemon.stats,
          damageRelations = damageRelations
        )
      }
      TabState.Move -> {
        item { MoveHeader() }
        items(pokemon.moves) {
          val moveDetail by remember(it.name) { viewModel.loadPokemonMove(it.name) }.collectAsState()
          Move(moveDetail)
        }
      }
      TabState.Evolution -> item {
        val evolutionChain by viewModel.evolutionChain.collectAsState()

        EvolutionList(
          modifier = modifier,
          chainList = evolutionChain
        )
      }
    }
  }
}

@Composable
private fun Header(
  viewPagerState: ViewPagerState,
  pokemonImageMap: Map<String, Flow<String>>,
  items: List<String>,
  pokemon: PokemonDetail,
  offset: Float
) {
  Box {
    val viewPagerHeight = THUMBNAIL_VIEWPAGER_HEIGHT * offset
    if (offset * 3 >= 1f) {
      PokemonImagePager(
        modifier = Modifier
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
          top = viewPagerHeight,
          start = 20.dp,
          end = 20.dp
        ),
      pokemon = pokemon
    )
  }
}

@Composable
private fun AppBar(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
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
        val imageUrl by remember { imageMap.getValue(pokemonName) }.collectAsState(initial = "")
        PokemonImage(imageUrl, getPagePosition())
      }
    }
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
      this.placeholder(R.drawable.ic_pokeball)
    },
    contentDescription = null
  )
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