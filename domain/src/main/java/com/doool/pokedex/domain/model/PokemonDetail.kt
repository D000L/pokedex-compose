package com.doool.pokedex.domain.model

import androidx.annotation.Keep

data class PokemonDetail(
  val id: Int,
  val name: String,
  val height: Int,
  val weight: Int,
  val image: String,
  val stats: List<Stat>,
  val types: List<Info>,
  val moves: List<Move>,
  val color: Info
)

data class Stat(
  val amount: Int,
  val name: String,
  val infoUrl: String,
)

@Keep
data class Move(
  val name: String,
  val url: String,
  val details: List<VersionGroupDetail> = listOf()
)

@Keep
data class VersionGroupDetail(
  val learnLevel: Int = 0,
  val learnMethod: Info = Info(),
  val version: Info = Info()
)