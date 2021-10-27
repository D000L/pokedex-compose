package com.doool.pokedex.presentation.ui.move_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.presentation.ui.common.PokemonType
import com.doool.pokedex.presentation.ui.widget.DefaultAppBar
import com.doool.pokedex.presentation.ui.widget.Type
import com.doool.pokedex.presentation.utils.Process
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.localized

@Composable
fun MoveInfoScreen(viewModel: MoveInfoViewModel = hiltViewModel()) {

  val move by viewModel.move.collectAsState(initial = LoadState.Loading)

  move.Process(onLoading = {

  }, onComplete = {
    Box {
      DefaultAppBar(title = "")
      Column {
        Text(text = it.id.toString())
        Text(text = it.names.localized)
        Text(text = it.flavorTextEntries.localized)
        Text(text = it.power.toString())
        Text(text = it.pp.toString())
        PokemonType.from(it.type.name).let { Type(it.typeColorResId, size = 18.dp, text = it.name) }
        Text(text = it.damageClass.name.capitalizeAndRemoveHyphen())
        Text(text = it.effectEntries.effect)
        Text(text = it.accuracy.toString())
        LazyColumn {
          items(it.learnedPokemon, key = { it.name }) {
            Text(text = it.name)
          }
        }
      }
    }
  })
}