package com.doool.pokedex.news.feature

import android.widget.TextView
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.pullRefreshIndicatorTransform
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.doool.pokedex.core.widget.Space
import com.doool.pokedex.core.widget.stickyAppBar
import com.doool.pokedex.domain.model.PokemonNews

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val news = viewModel.news.collectAsLazyPagingItems()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            news.refresh()
        })

    val pullRefreshModifier = Modifier.pullRefresh(pullRefreshState)

    Box(modifier = pullRefreshModifier, contentAlignment = Alignment.TopCenter) {
        NewsList(news)
        PokeBallIndicator(refreshing = isRefreshing, state = pullRefreshState)
    }

    LaunchedEffect(news.loadState.refresh) {
        if (news.loadState.refresh is LoadState.NotLoading)
            isRefreshing = false
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PokeBallIndicator(
    modifier: Modifier = Modifier,
    state: PullRefreshState,
    refreshing: Boolean
) {
    val indicatorSize = 40.dp

    Surface(
        modifier = modifier
            .size(indicatorSize)
            .pullRefreshIndicatorTransform(state, true),
        shape = CircleShape,
        elevation = if (refreshing) 16.dp else 0.dp
    ) {
        if (refreshing) {
            val transition = rememberInfiniteTransition()
            val degree by transition.animateFloat(
                initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1000,
                        easing = LinearEasing
                    )
                )
            )
            PokeBall(modifier = Modifier.rotate(degree), indicatorSize)
        } else {
            PokeBall(modifier = Modifier.rotate(state.progress * 180), indicatorSize)
        }
    }
}

@Composable
private fun PokeBall(modifier: Modifier = Modifier, size: Dp) {
    Canvas(modifier = modifier) {
        val length = size.toPx()
        val innerCircle = (size / 10).toPx()
        val strokeWidth = (size / 10).toPx()

        drawRect(color = Color.Red, size = Size(width = length, height = length / 2))
        drawRect(
            color = Color.White,
            topLeft = Offset(0f, length / 2),
            size = Size(width = length, height = length / 2)
        )
        drawCircle(color = Color.Black, radius = innerCircle, style = Stroke(strokeWidth))
        drawCircle(color = Color.White, radius = innerCircle, style = Fill)
        drawCircle(color = Color.Black, style = Stroke(strokeWidth))
    }
}

@Composable
private fun NewsList(news: LazyPagingItems<PokemonNews>) {
    val state = rememberLazyListState()

    LazyColumn(state = state) {
        stickyAppBar(state = state, title = R.string.pokemon_news_title)

        items(news, key = { it.title }) { news ->
            news ?: return@items
            News(news)
        }
    }
}

@Composable
private fun News(news: PokemonNews) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        NewsThumbnail(news.image)
        Space(height = 8.dp)
        NewsInfo(news)
    }
}

@Composable
private fun NewsThumbnail(imageUrl: String) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        model = imageUrl,
        contentDescription = null
    )
}

@Composable
private fun NewsInfo(news: PokemonNews) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.body1) {
        DataAndTags(news.date, news.tags)
        Space(height = 8.dp)
        HtmlText(html = news.title)
        Space(height = 4.dp)
        HtmlText(html = news.shortDescription)
        Space(height = 12.dp)
    }
}

@Composable
private fun DataAndTags(date: String, tags: String) {
    Row {
        Text(text = date)
        Space(width = 12.dp)
        Text(text = tags)
    }
}

@Composable
private fun HtmlText(html: String, style: TextStyle = LocalTextStyle.current) {
    AndroidView(factory = {
        TextView(it).apply {
            textSize = style.fontSize.value
        }
    }, update = {
        it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    })
}
