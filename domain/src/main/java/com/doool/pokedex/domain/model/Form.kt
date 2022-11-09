package com.doool.pokedex.domain.model

data class Form(
    val name: String = "",
    val formName: String = "",
    val formNames: List<LocalizedString> = listOf(),
    val formOrder: Int = 0,
    val id: Int = 0,
    val isBattleOnly: Boolean = false,
    val isDefault: Boolean = false,
    val isMega: Boolean = false,
    val pokemon: Info = Info(),
    val types: List<Info> = listOf(),
    val order: Int = 0
)