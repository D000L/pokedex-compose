package com.doool.pokedex.domain.model

data class PokemonDetail(
  val id: Int = -1,
  val name: String = "",
  val height: Int = 0,
  val weight: Int = 0,
  val image: String = "",
  val species: Info,
  val stats: List<Stat> = listOf(),
  val types: List<Info> = listOf(),
  val moves: List<Move> = listOf(),
  val abilities: List<AbilityInfo> = listOf()
)

data class Stat(
  val amount: Int = 0,
  val name: String = "",
  val infoUrl: String = ""
)

data class Move(
  val name: String = "",
  val url: String = "",
  val details: List<VersionGroupDetail> = listOf()
)

data class VersionGroupDetail(
  val learnLevel: Int = 0,
  val learnMethod: Info = Info(),
  val version: Info = Info()
)

data class AbilityInfo(
  val ability: Info = Info(),
  val isHidden: Boolean = false,
  val slot: Int = 0
)