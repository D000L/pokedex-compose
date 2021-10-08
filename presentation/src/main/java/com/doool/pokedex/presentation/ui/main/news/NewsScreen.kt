package com.doool.pokedex.presentation.ui.main.news

import android.widget.TextView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonNews
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.utils.getItemTopOffset

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {

  val news = viewModel.news.collectAsLazyPagingItems()

  NewsList(news)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsList(news: LazyPagingItems<PokemonNews>) {
  val state = rememberLazyListState()

  LazyColumn(state = state) {
    stickyHeader {
      val showHeaderDivider by derivedStateOf {
        state.getItemTopOffset(1) < 0
      }

      Box(
        Modifier
          .fillMaxWidth()
          .height(52.dp)
          .background(Color.White), contentAlignment = Alignment.Center
      ) {
        Text(text = "Pokemon News", fontSize = 20.sp)
        if (showHeaderDivider) Box(
          Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.Black)
        )
      }
    }
    items(news) {
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
      Text(text = news.date, fontSize = 14.sp)
      Space(width = 12.dp)
      Text(text = news.tags, fontSize = 14.sp)
    }
    Space(height = 8.dp)
    HtmlText(html = news.title, fontSize = 16.sp)
    Space(height = 4.dp)
    HtmlText(html = news.shortDescription, fontSize = 14.sp)
    Space(height = 12.dp)
  }
}

@Composable
private fun HtmlText(html: String, fontSize: TextUnit) {
  AndroidView(factory = {
    TextView(it).apply {
      textSize = fontSize.value
    }
  }, update = {
    it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
  })
}