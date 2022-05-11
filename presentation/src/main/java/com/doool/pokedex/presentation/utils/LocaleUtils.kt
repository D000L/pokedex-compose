package com.doool.pokedex.presentation.utils

import androidx.compose.runtime.Composable
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.presentation.Language
import com.doool.pokedex.presentation.LocalLanguage

val List<LocalizedString>.localized: String
    @Composable get() {
        val currentLanguage = LocalLanguage.current

        val localized = getLocalized(currentLanguage) ?: getLocalized(Language.English)
        return localized?.text ?: ""
    }

private fun List<LocalizedString>.getLocalized(language: Language) =
    firstOrNull { it.language == language.code }
