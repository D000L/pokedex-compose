package com.doool.pokedex.presentation.ui.move_info

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.presentation.ui.widget.DarkPokeball
import com.doool.pokedex.presentation.ui.widget.Space
import com.doool.pokedex.presentation.utils.Process
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.localized

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoveInfoScreen(viewModel: MoveInfoViewModel = hiltViewModel()) {

  val move by viewModel.move.collectAsState()

  Column(
    Modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = 240.dp)
      .padding(20.dp)
  ) {
    move.Process(onLoading = {

    }, onComplete = {
      Text(
        text = it.names.localized.capitalizeAndRemoveHyphen(),
        style = MaterialTheme.typography.subtitle1
      )
      Space(8.dp)
      Text(text = it.flavorTextEntries.localized, style = MaterialTheme.typography.body1)
      Space(10.dp)

      LazyVerticalGrid(
        cells = GridCells.Adaptive(80.dp),
        content = {
          items(it.learnedPokemon) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(14.dp)) {
              DarkPokeball(size = 80.dp, rotate = 0f)
              Image(
                modifier = Modifier.size(60.dp),
                painter = rememberImagePainter(it.imageUrl),
                contentDescription = null
              )
            }
          }
        })
    })
  }
}