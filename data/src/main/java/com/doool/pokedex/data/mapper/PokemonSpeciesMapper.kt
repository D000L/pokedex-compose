package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.InfoResponse
import com.doool.pokedex.data.response.PokemonSpeciesResponse
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonSpecies

fun PokemonSpeciesResponse.toModel(): PokemonSpecies = with(this) {
  PokemonSpecies(
    id = id,
    name = name,
    color = color.toModel(),
    evolutionUrl = evolutionChain?.url ?: "",
    flavorText = flavorTextEntries.map {
      it.flavorText
    },
    generation = generation.toModel()
  )
}

fun InfoResponse.toModel(): Info = with(this) {
  Info(name = name, url = url)
}