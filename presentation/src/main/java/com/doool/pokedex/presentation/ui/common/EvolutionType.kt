package com.doool.pokedex.presentation.ui.common

enum class EvolutionType(val text: String) {
    LevelUp("level-up"), Trade("trade"), Item("use-item"), Shed("shed"), Other("other");

    companion object {
        fun from(name: String): EvolutionType? {
            return values().find { it.text == name }
        }
    }
}
