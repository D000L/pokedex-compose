package com.doool.pokedex.presentation.ui.main.news

import androidx.compose.runtime.Composable
import com.doool.pokedex.presentation.ui.NavDestination

object NewsDestination : NavDestination() {
  override val route = "News"
  override val content: @Composable () -> Unit = { NewsScreen() }
}