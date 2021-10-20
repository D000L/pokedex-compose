package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.PokemonSpeciesResponse
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.domain.model.Genera
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonSpecies

fun PokemonSpeciesResponse.toModel(): PokemonSpecies = with(this) {
  PokemonSpecies(
    id = id,
    name = name,
    color = color.toModel(),
    maleRate = if (genderRate == 0) 100 else (1 - (1 / genderRate)) * 100,
    femaleRate = if (genderRate == 0) 0 else (1 / genderRate) * 100,
    evolutionUrl = evolutionChain?.url ?: "",
    flavorText = flavorTextEntries.map {
      it.flavorText.replace("\n", " ")
    },
    generation = generation.toModel(),
    genera = Genera(this.genera.first().genus),
    eggGroups = eggGroups.map { it.toModel() }
  )
}

fun InfoResponse.toModel(): Info = with(this) {
  Info(name = name, url = url)
}