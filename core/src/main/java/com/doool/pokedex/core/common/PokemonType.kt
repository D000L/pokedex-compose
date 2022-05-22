package com.doool.pokedex.core.common

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.doool.pokedex.core.R

enum class PokemonType(
    @DrawableRes val resId: Int,
    @ColorRes val typeColorResId: Int,
    @ColorRes val backgroundResId: Int,
) {
    Bug(R.drawable.bug, R.color.type_bug, R.color.background_bug),
    Dark(R.drawable.dark, R.color.type_dark, R.color.background_dark),
    Dragon(R.drawable.dragon, R.color.type_dragon, R.color.background_dragon),
    Electric(R.drawable.electric, R.color.type_electric, R.color.background_electric),
    Fairy(R.drawable.fairy, R.color.type_fairy, R.color.background_fairy),
    Fighting(R.drawable.fighting, R.color.type_fighting, R.color.background_fighting),
    Fire(R.drawable.fire, R.color.type_fire, R.color.background_fire),
    Flying(R.drawable.flying, R.color.type_flying, R.color.background_flying),
    Ghost(R.drawable.ghost, R.color.type_ghost, R.color.background_ghost),
    Grass(R.drawable.grass, R.color.type_grass, R.color.background_grass),
    Ground(R.drawable.ground, R.color.type_ground, R.color.background_ground),
    Ice(R.drawable.ice, R.color.type_ice, R.color.background_ice),
    Normal(R.drawable.normal, R.color.type_normal, R.color.background_normal),
    Poison(R.drawable.poison, R.color.type_poison, R.color.background_poison),
    Psychic(R.drawable.psychic, R.color.type_psychic, R.color.background_psychic),
    Rock(R.drawable.rock, R.color.type_rock, R.color.background_rock),
    Steel(R.drawable.steel, R.color.type_steel, R.color.background_steel),
    Water(R.drawable.water, R.color.type_water, R.color.background_water);

    companion object {
        fun from(name: String) = values().firstOrNull {
            it.name.lowercase() == name.lowercase()
        } ?: Bug
    }
}
