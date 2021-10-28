package com.doool.pokedex.presentation.ui.move_info

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.presentation.ui.common.PokemonType
import com.doool.pokedex.presentation.ui.widget.*
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
      CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
    }, onComplete = {
      val type = PokemonType.from(it.type.name)
      val color = colorResource(id = type.backgroundResId)

      Row {
        Text(
          text = it.names.localized.capitalizeAndRemoveHyphen(),
          style = MaterialTheme.typography.subtitle1
        )
        SpaceFill()
        Type(type = type, text = type.name)
      }
      Space(12.dp)
      Text(text = it.flavorTextEntries.localized, style = MaterialTheme.typography.body1)
      Space(20.dp)

      Row(
        Modifier
          .fillMaxWidth()
          .height(60.dp)
          .border(
            width = 0.5.dp,
            color = color,
            shape = CircleShape
          ), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        Item("Power", color, it.power)
        Item("Acc", color, it.accuracy)
        Item("PP", color, it.pp)
      }

      Space(20.dp)

      LazyVerticalGrid(
        cells = GridCells.Adaptive(100.dp),
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

@Composable
private fun Item(title: String, color: Color, amount: Int) {
  Column(Modifier.width(60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = title, style = MaterialTheme.typography.body2, color = color)
    Text(text = amount.toString(), style = MaterialTheme.typography.body1)
  }
}