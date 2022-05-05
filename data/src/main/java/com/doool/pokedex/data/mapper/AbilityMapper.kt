package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.AbilityResponse
import com.doool.pokedex.domain.model.Ability
import com.doool.pokedex.domain.model.LocalizedString

fun AbilityResponse.toModel(): Ability = with(this) {
    Ability(
        id,
        name,
        names = names.map { LocalizedString(it.name, it.language.name) },
        effectEntries = effectEntries.map { it.toModel() },
        flavorTextEntries = flavorTextEntries.map { LocalizedString(it.flavorText, it.language.name) })
}