package com.doool.pokedex.presentation.ui.news.destination

import androidx.compose.runtime.Composable
import com.doool.core.nav.NavDestination
import com.doool.pokedex.news.NewsScreen

object NewsDestination : NavDestination() {
    override val route = "News"
    override val content: @Composable () -> Unit = { NewsScreen() }
}
