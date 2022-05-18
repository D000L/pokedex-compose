package com.doool.pokedex.presentation.ui.move.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.doool.core.nav.LocalNavController
import com.doool.core.widget.stickyAppBar
import com.doool.pokedex.presentation.ui.move.R
import com.doool.pokedex.presentation.ui.move.info.MoveInfoDestination

@Composable
fun MoveListScreen(viewModel: MoveListViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    val state = rememberLazyListState()

    val moveList by viewModel.moveList.collectAsState(initial = emptyList())

    val onMoveClicked by rememberUpdatedState<(String) -> Unit> {
        navController.navigate(MoveInfoDestination.getRouteByName(it))
    }

    LazyColumn(state = state) {
        stickyAppBar(state = state, title = R.string.pokemon_move_title)

        item {
            MoveHeaders(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp))
        }

        items(moveList, key = { it.id }) { move ->
            val moveState by remember(move) { viewModel.getMove(move.name) }.collectAsState()

            Move(moveState = moveState, onItemClicked = onMoveClicked)
        }
    }
}
