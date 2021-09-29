package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.*
import com.doool.pokedex.domain.model.*

fun PokemonDetailResponse.toModel(): PokemonDetail = with(this) {
  PokemonDetail(
    id = id,
    name = name,
    height = height,
    weight = weight,
    image = sprites.other.artwork.frontDefault,
    stats = stats.map {
      it.toModel()
    },
    types = types.map {
      it.toModel()
    },
    moves = moves.map {
      it.toModel()
    },
    color = color.toModel()
  )
}

fun StatResponse.toModel(): Stat = with(this) {
  Stat(amount = baseStat, name = stat.name, infoUrl = stat.url)
}

fun TypeResponse.toModel(): Info = with(this) {
  Info(name = type.name, url = type.url)
}

fun MoveResponse.toModel(): Move = with(this) {
  Move(name = move.name, url = move.url, details = versionGroupDetails.map {
    it.toModel()
  })
}

fun VersionGroupDetailResponse.toModel(): VersionGroupDetail = with(this) {
  VersionGroupDetail(
    learnLevel = levelLearnedAt,
    learnMethod = moveLearnMethod.toModel(),
    version = versionGroup.toModel()
  )
}
