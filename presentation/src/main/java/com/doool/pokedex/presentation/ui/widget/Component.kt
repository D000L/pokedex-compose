package com.doool.pokedex.presentation.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun Space(width: Dp = 0.dp, height: Dp = 0.dp) {
  Spacer(modifier = Modifier.size(height))
}

@Composable
fun ColumnScope.Space(height: Dp = 0.dp) {
  Spacer(modifier = Modifier.height(height))
}

@Composable
fun RowScope.Space(width: Dp = 0.dp) {
  Spacer(modifier = Modifier.width(width))
}

@Composable
fun ColumnScope.SpaceFill() {
  Spacer(modifier = Modifier.weight(1f))
}

@Composable
fun RowScope.SpaceFill() {
  Spacer(modifier = Modifier.weight(1f))
}