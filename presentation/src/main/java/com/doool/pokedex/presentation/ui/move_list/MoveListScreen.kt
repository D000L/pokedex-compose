package com.doool.pokedex.presentation.ui.move_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.pokedex.presentation.LocalNavController
import com.doool.pokedex.presentation.ui.move_info.destination.MoveInfoDestination
import com.doool.pokedex.presentation.ui.pokemon_info.Move
import com.doool.pokedex.presentation.ui.pokemon_info.MoveHeader
import com.doool.pokedex.presentation.ui.widget.listAppBar

@Composable
fun MoveListScreen(viewModel: MoveListViewModel = hiltViewModel()) {
  val navController = LocalNavController.current
  val state = rememberLazyListState()

  val moveList by viewModel.moveList.collectAsState(initial = emptyList())

  LazyColumn(state = state) {
    listAppBar(state = state, title = "Move")
    item { MoveHeader() }
    items(moveList, key = { it.id }) { it ->
      val move by remember(it) { viewModel.getMove(it.name) }.collectAsState()
      Move(move) {
        navController.navigate(MoveInfoDestination.getRouteByName(it))
      }
    }
  }
}