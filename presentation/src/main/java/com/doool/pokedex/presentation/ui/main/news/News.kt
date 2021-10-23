package com.doool.pokedex.presentation.ui.main.news

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.doool.pokedex.presentation.ui.main.NavDestination

object NewsDestination : NavDestination() {
  override val route = "News"
  override val content: @Composable () -> Unit = { NewsScreen() }
}