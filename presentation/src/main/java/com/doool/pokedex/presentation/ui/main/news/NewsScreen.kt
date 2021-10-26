package com.doool.pokedex.presentation.ui.main.news

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonNews
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.ui.main.common.listAppBar

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {

  val news = viewModel.news.collectAsLazyPagingItems()

  NewsList(news)
}

@Composable
fun NewsList(news: LazyPagingItems<PokemonNews>) {
  val state = rememberLazyListState()

  LazyColumn(state = state) {
    listAppBar(state = state, title = "Pokemon News")
    items(news, key = { it.title }) {
      it?.let { News(it) }
    }
  }
}

@Composable
fun News(news: PokemonNews) {
  Column(modifier = Modifier.padding(horizontal = 20.dp)) {
    Image(
      modifier = Modifier
        .fillMaxWidth()
        .height(400.dp),
      painter = rememberImagePainter(news.image),
      contentDescription = null
    )
    Space(height = 8.dp)
    Row {
      Text(text = news.date, style = MaterialTheme.typography.body1)
      Space(width = 12.dp)
      Text(text = news.tags, style = MaterialTheme.typography.body1)
    }
    Space(height = 8.dp)
    HtmlText(html = news.title, style = MaterialTheme.typography.body1)
    Space(height = 4.dp)
    HtmlText(html = news.shortDescription, style = MaterialTheme.typography.body1)
    Space(height = 12.dp)
  }
}

@Composable
private fun HtmlText(html: String, style: TextStyle) {
  AndroidView(factory = {
    TextView(it).apply {
      textSize = style.fontSize.value
    }
  }, update = {
    it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
  })
}