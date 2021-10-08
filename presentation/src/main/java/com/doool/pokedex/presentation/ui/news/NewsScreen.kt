package com.doool.pokedex.presentation.ui.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.model.PokemonNews
import com.doool.pokedex.presentation.ui.pokemon.PokemonViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {

  val news = viewModel.news.collectAsLazyPagingItems()

  NewsList(news)
}

@Composable
fun NewsList(news: LazyPagingItems<PokemonNews>) {
  LazyColumn {
    items(news) {
      it?.let { News(it) }
    }
  }
}

@Composable
fun News(news: PokemonNews) {
  Column {
    Image(painter = rememberImagePainter(news.image), contentDescription = null)
    Row {
      Text(text = news.date, fontSize = 13.sp)
      Text(text = news.tags, fontSize = 13.sp)
    }
    Text(text = news.title, fontSize = 14.sp)
    Text(text = news.shortDescription, fontSize = 13.sp)
  }
}