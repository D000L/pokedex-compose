package com.doool.pokedex.presentation.setting

import androidx.compose.runtime.compositionLocalOf

data class PokedexSetting(val language: Language = Language.Korea)

enum class Language(val localeName: String) {
  English("en"), Russia("ru"), Korea("ko"), French("fr"), German("de"),
  Spanish("es"), Italian("it"), Japan("ja")
}

val LocalSettings = compositionLocalOf { PokedexSetting() }