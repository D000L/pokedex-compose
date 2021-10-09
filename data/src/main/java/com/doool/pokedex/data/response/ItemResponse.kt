package com.doool.pokedex.data.response


import androidx.annotation.Keep
import com.doool.pokedex.data.response.common.*
import com.google.gson.annotations.SerializedName

@Keep
data class ItemResponse(
  val attributes: List<InfoResponse> = listOf(),
  @SerializedName("baby_trigger_for") val babyTriggerFor: Any = Any(),
  val category: InfoResponse = InfoResponse(),
  val cost: Int = 0,
  @SerializedName("effect_entries") val effectEntries: List<EffectResponse> = listOf(),
  @SerializedName("flavor_text_entries") val flavorTextEntries: List<VersionGroupFlavorText> = listOf(),
  @SerializedName("fling_effect") val flingEffect: Any = Any(),
  @SerializedName("fling_power") val flingPower: Any = Any(),
  @SerializedName("game_indices") val gameIndices: List<GameIndice> = listOf(),
  @SerializedName("held_by_pokemon")
  val heldByPokemon: List<Any> = listOf(),
  val id: Int = 0,
  val machines: List<Any> = listOf(),
  val name: String = "",
  val names: List<Names> = listOf(),
  val sprites: Sprites = Sprites()
)