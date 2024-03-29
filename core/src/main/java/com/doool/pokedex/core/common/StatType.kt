package com.doool.pokedex.core.common

enum class StatType(val statName: String) {
    Hp("HP"),
    Attack("Attack"),
    Defense("Defense"),
    SpecialAttack("Sp. Atk"),
    SpecialDefense("Sp. Def"),
    Speed("Speed");

    companion object {
        fun from(name: String): StatType {
            return when (name) {
                "hp" -> Hp
                "attack" -> Attack
                "defense" -> Defense
                "special-attack" -> SpecialAttack
                "special-defense" -> SpecialDefense
                "speed" -> Speed
                else -> Hp
            }
        }
    }
}
