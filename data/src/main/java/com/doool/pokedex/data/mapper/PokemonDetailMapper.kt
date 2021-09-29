package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.PokemonDetailResponse
import com.doool.pokedex.data.response.StatEntity
import com.doool.pokedex.data.response.TypeEntity
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.Stat

fun PokemonDetailResponse.toModel(): PokemonDetail = with(this) {
  PokemonDetail(
    id = id,
    name = name,
    height = height,
    weight = weight,
    image = sprites.frontDefault,
    stats = stats.map {
      it.toModel()
    },
    types = types.map {
      it.toModel()
    })
}

fun StatEntity.toModel(): Stat = with(this) {
  Stat(amount = baseStat, name = stat.name, infoUrl = stat.url)
}

fun TypeEntity.toModel(): Info = with(this) {
  Info(name = type.name, url = type.url)
}