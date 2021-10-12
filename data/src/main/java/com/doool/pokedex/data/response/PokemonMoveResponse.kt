package com.doool.pokedex.data.response

import androidx.annotation.Keep
import com.doool.pokedex.data.response.common.EffectResponse
import com.doool.pokedex.data.response.common.FlavorText
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.data.response.common.Names
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonMoveResponse(
  val id: Int = 0,
  val name: String = "",
  val accuracy: Int = 0,
  @SerializedName("effect_chance") val effectChance: Int = 0,
  @SerializedName("damage_class") val damageClass: InfoResponse = InfoResponse(),
  @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorText> = emptyList(),
  @SerializedName("effect_entries") val effectEntries: List<EffectResponse> = emptyList(),
  @SerializedName("learned_by_pokemon") val learnedPokemon: List<InfoResponse> = emptyList(),
  val machines: List<MachinesResponse> = emptyList(),
  val generation: InfoResponse,
  val names: List<Names> = emptyList(),
  val power: Int = 0,
  val pp: Int = 0,
  val type: InfoResponse = InfoResponse()
)

@Keep
data class MachinesResponse(
  val machine: InfoResponse,
  @SerializedName("version_group") val versionGroup: InfoResponse = InfoResponse()
)