package com.doool.pokedex.domain.model

data class Ability(
    val id: Int = 0,
    val name: String = "",
    val names: List<LocalizedString> = emptyList(),
    val effectEntries: List<Effect> = emptyList(),
    val flavorTextEntries: List<LocalizedString> = emptyList()
)