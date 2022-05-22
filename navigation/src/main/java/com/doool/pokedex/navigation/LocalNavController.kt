package com.doool.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

object LocalNavController {

    private val LocalNavController = compositionLocalOf<NavHostController?> { null }

    val current: NavHostController
        @Composable get() {
            return LocalNavController.current ?: NavHostController(LocalContext.current)
        }

    infix fun provides(
        navHostController: NavHostController
    ): ProvidedValue<NavHostController?> = LocalNavController.provides(navHostController)
}
