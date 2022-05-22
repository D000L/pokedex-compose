package com.doool.pokedex.news.feature

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.doool.pokedex.core.widget.Space
import com.doool.pokedex.core.widget.stickyAppBar
import com.doool.pokedex.domain.model.PokemonNews
import com.doool.pokedex.news.feature.R

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val news = viewModel.news.collectAsLazyPagingItems()

    NewsList(news)
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

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun NewsThumbnail(imageUrl: String) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        painter = rememberImagePainter(imageUrl),
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
