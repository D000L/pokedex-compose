package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.entity.PokemonEntity
import com.doool.pokedex.domain.model.Pokemon

fun PokemonEntity.toModel(): Pokemon = with(this) {
  Pokemon(
    name = name,
    url = url
  )
}