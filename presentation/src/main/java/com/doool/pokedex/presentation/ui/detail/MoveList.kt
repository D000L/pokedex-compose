package com.doool.pokedex.presentation.ui.detail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.doool.pokedex.domain.model.Move

@Composable
fun MoveList(moves: List<Move>) {
  LazyColumn {
    items(moves) {
      Move(it)
    }
  }
}

@Composable
fun Move(move: Move) {
  Text(text = move.name)
}