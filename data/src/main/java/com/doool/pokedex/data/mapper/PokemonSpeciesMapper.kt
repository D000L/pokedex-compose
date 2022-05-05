package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.parseId
import com.doool.pokedex.data.response.PokemonSpeciesResponse
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.model.PokemonSpecies

fun PokemonSpeciesResponse.toModel(): PokemonSpecies = with(this) {
    val maleRate = calculateGenderRate(true, genderRate)
    val femaleRate = calculateGenderRate(false, genderRate)
    PokemonSpecies(
        id = id,
        name = name,
        names = names.map { LocalizedString(it.name, it.language.name) },
        color = color.toModel(),
        maleRate = maleRate,
        femaleRate = femaleRate,
        isGenderless = genderRate == -1,
        evolutionUrl = evolutionChain?.url ?: "",
        flavorText = flavorTextEntries.map {
            LocalizedString(it.flavorText.replace("\n", " "), it.language.name)
        },
        generation = generation.toModel(),
        genera = genera.map { LocalizedString(it.genus, it.language.name) },
        eggGroups = eggGroups.map { it.toModel() }
    )
}

fun InfoResponse.toModel(): Info = with(this) {
    Info(name = name, id = url.parseId())
}

private fun calculateGenderRate(isMale: Boolean, rate: Int): Int {
    return when (rate) {
        -1 -> -1
        0 -> if (isMale) 100 else 0
        1 -> 50
        else -> when {
            isMale -> 100 - 1f / rate * 100
            else -> (1f / rate) * 100
        }
    }.toInt()
}