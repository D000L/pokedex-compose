package com.doool.pokedex.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonMoveResponse(
  val id: Int = 0,
  val name: String = "",
  val accuracy: Int = 0,
  @SerializedName("effect_chance") val effectChance: Int = 0,
  @SerializedName("damage_class") val damageClass: InfoResponse = InfoResponse(),
  @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntries> = emptyList(),
  @SerializedName("effect_entries") val effectEntries: List<EffectResponse> = emptyList(),
  val names: List<Names> = emptyList(),
  val power: Int = 0,
  val pp: Int = 0,
  val type: InfoResponse = InfoResponse()
)

@Keep
data class EffectResponse(
  val effect: String = "",
  val language: InfoResponse = InfoResponse(),
  @SerializedName("short_effect") val shortEffect: String = ""
)


