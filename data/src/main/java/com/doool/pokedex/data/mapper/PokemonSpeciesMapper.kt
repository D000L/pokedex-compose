package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.parseId
import com.doool.pokedex.data.response.PokemonSpeciesResponse
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.model.PokemonSpecies

fun PokemonSpeciesResponse.toModel(): PokemonSpecies = with(this) {
  PokemonSpecies(
    id = id,
    name = name,
    names = names.map { LocalizedString(it.name, it.language.name) },
    color = color.toModel(),
    maleRate = if (genderRate == 0) 100 else (1 - (1 / genderRate)) * 100,
    femaleRate = if (genderRate == 0) 0 else (1 / genderRate) * 100,
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