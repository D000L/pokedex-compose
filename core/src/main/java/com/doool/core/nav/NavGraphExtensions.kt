package com.doool.core.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

fun NavGraphBuilder.composable(
    navDestination: NavDestination
) = composable(navDestination.route, arguments = navDestination.arguments) {
    navDestination.content()
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.bottomSheet(
    navDestination: NavDestination
) = bottomSheet(navDestination.route, arguments = navDestination.arguments) {
    navDestination.content()
}
