package com.doool.pokedex.pokemon.info

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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.core.LocalPokemonColor
import com.doool.core.nav.LocalNavController
import com.doool.core.utils.capitalizeAndRemoveHyphen
import com.doool.core.utils.defaultPlaceholder
import com.doool.core.utils.getItemTopOffset
import com.doool.core.utils.localized
import com.doool.core.widget.DefaultAppBar
import com.doool.core.widget.Space
import com.doool.core.widget.SpaceFill
import com.doool.core.widget.TOOLBAR_HEIGHT
import com.doool.core.widget.TypeListWithTitle
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.getData
import com.doool.pokedex.domain.isLoading
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.move.info.MoveInfoDestination
import com.doool.pokedex.presentation.extensions.getBackgroundColor
import com.doool.pokedex.pokemon.info.model.HeaderUIModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
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
        val topOffset = lazyListState.getItemTopOffset(1)
        val height = density.run { (topOffset.toDp() - HEADER_HEIGHT_EXCLUDE_PAGER) }
        (height / THUMBNAIL_VIEWPAGER_HEIGHT).coerceIn(0f, 1f)
    }

    val dragged by lazyListState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!dragged && offset != 0f && offset != 1f) {
            val direction = if (offset > 0.5f) -offset else offset
            lazyListState.animateScrollBy(direction * density.run { THUMBNAIL_VIEWPAGER_HEIGHT.toPx() })
        }
    }

    BoxWithConstraints {
        val minHeight = maxHeight - HEADER_HEIGHT_EXCLUDE_PAGER
        val headerState by viewModel.headerState.collectAsState()

        CompositionLocalProvider(
            LocalPokemonColor provides colorResource(
                id = headerState.getData()?.types?.getBackgroundColor() ?: R.color.background_bug
            )
        ) {
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
            DefaultAppBar(backButtonColor = Color.White, showDivider = false)
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
    val moveUIState by viewModel.moveUIState.collectAsState()

    LazyColumn(
        state = state
    ) {
        item { Box(Modifier.padding(top = HEADER_HEIGHT)) }
        when (tabState) {
            TabState.About -> item("About") {
                val aboutUIState by viewModel.aboutUIState.collectAsState()
                About(
                    modifier = modifier.padding(20.dp),
                    aboutUIState = aboutUIState
                )
            }
            TabState.Stats -> item("Stats") {
                val statsUIState by viewModel.statsUIState.collectAsState()

                PokemonStats(
                    modifier = modifier.padding(20.dp),
                    statsUIState = statsUIState,
                )
            }
            TabState.Move -> {
                item {
                    com.doool.pokedex.move.list.MoveHeaders(
                        Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 20.dp,
                            bottom = 10.dp
                        )
                    )
                }
                if (moveUIState.isLoading()) {
                    item {
                        Box(Modifier.fillParentMaxSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    }
                } else {
                    moveUIState.getData()?.let { moveItems ->
                        items(moveItems.moves, key = { it.name }) {
                            val move by remember(it.name) { viewModel.loadPokemonMove(it.name) }.collectAsState()
                            com.doool.pokedex.move.list.Move(move) {
                                navController.navigate(MoveInfoDestination.getRouteByName(it))
                            }
                        }
                    }
                }
            }
            TabState.Evolution -> item("Evolution") {
                val evolutionListUIState by viewModel.evolutionListUIState.collectAsState()

                EvolutionList(
                    modifier = modifier.padding(20.dp),
                    evolutionListUIState = evolutionListUIState,
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
    headerUIModel: LoadState<HeaderUIModel>,
    offset: Float
) {
    Box {
        val viewPagerHeight = THUMBNAIL_VIEWPAGER_HEIGHT * offset

        if (viewPagerHeight > THUMBNAIL_VIEWPAGER_HEIGHT - TITLE_HEIGHT) {
            HorizontalPager(
                modifier = Modifier
                    .requiredHeight(THUMBNAIL_VIEWPAGER_HEIGHT)
                    .alpha(offset.coerceIn(0f, 1f)),
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
            headerUIState = headerUIModel
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
private fun TitleLayout(modifier: Modifier, headerUIState: LoadState<HeaderUIModel>) {
    val isLoading = headerUIState.isLoading()
    val headerUIModel = headerUIState.getData() ?: HeaderUIModel()

    Column(modifier.height(TITLE_HEIGHT)) {
        Text(
            modifier = Modifier.defaultPlaceholder(isLoading),
            text = headerUIModel.formNames.localized.capitalizeAndRemoveHyphen(),
            style = MaterialTheme.typography.subtitle1,
            color = Color.White.copy(alpha = 0.5f)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.defaultPlaceholder(isLoading),
                text = headerUIModel.names.localized.capitalizeAndRemoveHyphen(),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.White
            )
            SpaceFill()
            Text(
                modifier = Modifier.defaultPlaceholder(isLoading),
                text = "#%03d".format(headerUIModel.id),
                style = MaterialTheme.typography.h3,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
        Space(height = 6.dp)
        TypeListWithTitle(
            modifier = Modifier.defaultPlaceholder(isLoading),
            types = headerUIModel.types
        )
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
