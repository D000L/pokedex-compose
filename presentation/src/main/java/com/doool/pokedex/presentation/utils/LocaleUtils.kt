package com.doool.pokedex.presentation.utils

import androidx.compose.runtime.Composable
import com.doool.pokedex.domain.model.Language
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.presentation.LocalSettings

val List<LocalizedString>.localized: String
  @Composable get() {
    val currentLanguage = LocalSettings.current.language
    return (firstOrNull { it.language == currentLanguage.localeName }
      ?: this.firstOrNull { it.language == Language.English.localeName })?.text ?: ""
  }