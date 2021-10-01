package com.doool.pokedex.domain.model

import androidx.annotation.Keep

data class PokemonDetail(
  val id: Int = 0,
  val name: String = "",
  val height: Int = 0,
  val weight: Int = 0,
  val image: String = "",
  val stats: List<Stat> = listOf(),
  val types: List<Info> = listOf(),
  val moves: List<Move> = listOf(),
  val color: Info = Info()
)

data class Stat(
  val amount: Int = 0,
  val name: String = "",
  val infoUrl: String = ""
)

@Keep
data class Move(
  val name: String = "",
  val url: String = "",
  val details: List<VersionGroupDetail> = listOf()
)

@Keep
data class VersionGroupDetail(
  val learnLevel: Int = 0,
  val learnMethod: Info = Info(),
  val version: Info = Info()
)