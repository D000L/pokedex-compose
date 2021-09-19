package com.doool.pokedex.data.entity

import com.google.gson.annotations.SerializedName

data class PokemonDetailEntity(
  val name: String,
  val height: Int,
  val weight: Int,
  val sprites: Sprites,
  val stats: List<StatEntity>,
  val order: Int
)

data class Sprites(
  @SerializedName("front_default") val frontDefault: String,
  @SerializedName("back_default") val backDefault: String
)

data class StatEntity(
  @SerializedName("base_stat") val amount: Int,
  val effort: Int,
  @SerializedName("stat") val info: StatInfo
)

data class StatInfo(
  val name: String,
  @SerializedName("url") val statInfoUrl: String,
)