package com.doool.pokedex.domain.model

data class LocalizedInfo(
    val id: Int = 0,
    val name: String = "",
    var names: List<LocalizedString> = emptyList(),
    var imageUrl: String = ""
)