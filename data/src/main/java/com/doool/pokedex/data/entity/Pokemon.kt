package com.doool.pokedex.data.entity

import com.google.gson.annotations.SerializedName

data class Pokemon(
  val name : String,
  val height : Int,
  val weight : Int,
  val sprites: Sprites,
  val stats : List<Stat>,
  val order : Int
)

data class Sprites(
  @SerializedName("front_default") val frontDefault : String,
  @SerializedName("back_default") val backDefault : String
)

data class Stat(
  @SerializedName("base_stat") val stat : Int,
  val effort : Int,
  @SerializedName("stat") val info : StatInfo
)

data class StatInfo(
  val name : String,
  @SerializedName("url") val statInfoUrl: String,
)