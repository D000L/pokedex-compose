package com.doool.pokedex.presentation.ui.main.move

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.presentation.ui.main.LocalNavController
import com.doool.pokedex.presentation.ui.main.common.Type
import com.doool.pokedex.presentation.ui.main.common.toPokemonType
import com.doool.pokedex.presentation.ui.main.pokemon.detail.Move
import com.doool.pokedex.presentation.ui.main.pokemon.detail.MoveHeader
import com.doool.pokedex.presentation.utils.Process
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen

@Composable
fun MoveScreen(viewModel: MoveViewModel = hiltViewModel()) {

  val moveList by viewModel.moveList.collectAsState(initial = emptyList())

  val navController = LocalNavController.current

  LazyColumn(contentPadding = PaddingValues(top = 20.dp)) {
    item { MoveHeader() }
    items(moveList, key = { it }) {
      val move by remember(it) { viewModel.getMove(it) }.collectAsState()
      Move(move) {
        navController.navigate(MoveInfoDestination.getRouteByName(it))
      }
    }
  }
}

@Composable
fun MoveInfoScreen(viewModel: MoveInfoViewModel = hiltViewModel()) {

  val move by viewModel.move.collectAsState(initial = LoadState.Loading)

  move.Process(onLoading = {

  }, onComplete = {
    Column() {
      Text(text = it.id.toString())
      Text(text = it.name)
      Text(text = it.flavorTextEntries.first())
      Text(text = it.power.toString())
      Text(text = it.pp.toString())
      it.type.name.toPokemonType().let { Type(it.typeColorResId, size = 18.dp, text = it.name) }
      Text(text = it.damageClass.name.capitalizeAndRemoveHyphen())
      Text(text = it.effectEntries.effect)
      Text(text = it.accuracy.toString())
      LazyColumn {
        items(it.learnedPokemon, key = { it.name }) {
          Text(text = it.name)
        }
      }
    }
  })
}