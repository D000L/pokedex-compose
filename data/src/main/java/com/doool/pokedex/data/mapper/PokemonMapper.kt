package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.StatEntity
import com.doool.pokedex.data.entity.TypeEntity
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.Stat
import com.doool.pokedex.domain.model.Type

fun PokemonDetailEntity.toModel(): PokemonDetail = with(this) {
  PokemonDetail(
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
  Stat(amount = amount, name = info.name, infoUrl = info.statInfoUrl)
}

fun TypeEntity.toModel(): Type = with(this) {
  Type(name = type.name, url = type.url)
}