package com.doool.pokedex.presentation.utils

import androidx.compose.runtime.Composable
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.presentation.Language
import com.doool.pokedex.presentation.LocalLanguage

val List<LocalizedString>.localized: String
  @Composable get() {
    val currentLanguage = LocalLanguage.current
    return (firstOrNull { it.language == currentLanguage.code }
      ?: this.firstOrNull { it.language == Language.English.code })?.text ?: ""
  }