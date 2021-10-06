package com.doool.pokedex.presentation.ui.detail

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.presentation.ui.common.Space
import com.doool.pokedex.presentation.ui.common.SpaceFill
import com.doool.pokedex.presentation.ui.common.TypeListWithTitle
import com.doool.pokedex.presentation.ui.common.toPokemonColor
import com.doool.viewpager.ViewPager
import com.doool.viewpager.ViewPagerOrientation
import com.doool.viewpager.ViewPagerState
import com.doool.viewpager.rememberViewPagerState
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs

private val TOOLBAR_HEIGHT = 56.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollapsingToolbarLayout(
  min: Float = 0f,
  max: Float = 1f,
  toolbar: @Composable (Float) -> Unit,
  content: @Composable ColumnScope.() -> Unit
) {
  val state = rememberSwipeableState(initialValue = 0f)

  Column(
    Modifier
      .swipeable(state, anchors = mapOf(min to 0f, max to 1f), Orientation.Vertical)
      .nestedScroll(object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
          val used = state.performDrag(available.y)
          return super.onPreScroll(Offset(0f, available.y - used), source)
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
          state.performFling(available.y)
          return super.onPreFling(available)
        }
      })
  ) {
    Box(Modifier.verticalScroll(rememberScrollState())) {
      toolbar((state.offset.value - min) / (max - min))
    }
    content()
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
  initPokemonId: Int = 1,
  viewModel: PokemonDetailViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {}
) {
  Log.d("composable update", "DetailScreen")
  val items = (1..800).toList()

  val viewPagerState = rememberViewPagerState(currentPage = initPokemonId - 1)

  LaunchedEffect(viewPagerState.currentPage) {
    viewModel.setCurrentItem(items[viewPagerState.currentPage])
  }

  val pokemon by remember { viewModel.getPokemon() }.collectAsState(PokemonDetail())

  CollapsingToolbarLayout(0f, 600f, toolbar = { offset ->
    val mainColor by animateColorAsState(targetValue = colorResource(pokemon.color.name.toPokemonColor().colorRes))

    BoxWithConstraints(
      modifier = Modifier.background(color = mainColor)
    ) {
      val width = LocalDensity.current.run { maxWidth.toPx() }
      val offsetY = LocalDensity.current.run { -5.dp.toPx() }

      DetailAppBar(modifier = Modifier.height(TOOLBAR_HEIGHT), onClickBack = navigateBack)

      if ((offset * 3).coerceIn(0f, 1f) <= 1f) {
        PokemonImagePager(
          modifier = Modifier
            .padding(top = ((TOOLBAR_HEIGHT + 0.dp) * offset).coerceAtLeast(0.dp))
            .height(180.dp * offset)
            .graphicsLayer {
              alpha = (offset * 3).coerceIn(0f, 1f)
            },
          items = items,
          viewPagerState = viewPagerState,
          width = width,
          offsetY = offsetY
        ) {
          viewModel.loadPokemonImage(it)
        }
      }

      Column(
        Modifier
          .padding(horizontal = 20.dp)
          .padding(bottom = 20.dp)
      ) {
        Row(
          Modifier
            .padding(top = (TOOLBAR_HEIGHT) + 180.dp * offset),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = pokemon.name,
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
  }) {
    DetailPage(remember { viewModel.getUiState() })
  }
}

@Composable
fun DetailAppBar(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
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

  Box(modifier) {
    ViewPager(
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
fun DetailPage(uiState: Flow<DetailUiState>) {
  val uiState by uiState.collectAsState(initial = DetailUiState())

  Log.d("composable update", "DetailPage")
  DetailTabLayout {
    when (it) {
      TabState.Detail -> Info(uiState.pokemon, uiState.species)
      TabState.Stats -> Stats(
        stats = uiState.pokemon.stats,
        damageRelations = uiState.damageRelations
      )
      TabState.Move -> MoveList(uiState.pokemon.moves)
      TabState.Evolution -> EvolutionList(uiState.evolutionChain)
    }
  }
}

enum class TabState {
  Detail, Stats, Move, Evolution
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