package com.doool.pokedex.core

import androidx.annotation.DrawableRes
import androidx.compose.runtime.compositionLocalOf
import com.doool.pokedex.core.R

enum class Language(val code: String, val title: String, @DrawableRes val flagResId: Int) {
    English("en", "English", R.drawable.english),
    Korea("ko", "한국어", R.drawable.korea),
    France("fr", "Français", R.drawable.france),
    German("de", "Deutsch", R.drawable.german),
    Spain("es", "Español", R.drawable.spain),
    Japan("ja", "日本語", R.drawable.japan);

    companion object {
        fun fromCode(code: String?) = values().firstOrNull {
            it.code == code
        } ?: English
    }
}

val LocalLanguage = compositionLocalOf { Language.English }
