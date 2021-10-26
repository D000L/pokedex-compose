package com.doool.pokedex.domain.model

enum class Language(val localeName: String, val title: String) {
  English("en", "English"), Korea("ko", "한국어"), French("fr", "Français"), German("de", "Deutsch"),
  Spanish("es", "Español"), Japan("ja", "日本語")
}