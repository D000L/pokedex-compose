package com.doool.pokedex.presentation.ui.news.destination

import androidx.compose.runtime.Composable
import com.doool.pokedex.presentation.NavDestination
import com.doool.pokedex.presentation.ui.news.NewsScreen

object NewsDestination : NavDestination() {
    override val route = "News"
    override val content: @Composable () -> Unit = { NewsScreen() }
}