package com.doool.pokedex.presentation

import androidx.compose.runtime.compositionLocalOf
import com.doool.pokedex.domain.model.PokedexSetting

val LocalSettings = compositionLocalOf { PokedexSetting() }