package com.doool.pokedex.data.response


import androidx.annotation.Keep
import com.doool.pokedex.data.response.common.InfoResponse
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonTypeResistanceResponse(
  @SerializedName("damage_relations") val damageRelations: DamageRelationsResponse = DamageRelationsResponse(),
  val id: Int = 0,
  val name: String = ""
)

@Keep
data class DamageRelationsResponse(
  @SerializedName("double_damage_from") val doubleDamageFrom: List<InfoResponse> = listOf(),
  @SerializedName("double_damage_to") val doubleDamageTo: List<InfoResponse> = listOf(),
  @SerializedName("half_damage_from") val halfDamageFrom: List<InfoResponse> = listOf(),
  @SerializedName("half_damage_to") val halfDamageTo: List<InfoResponse> = listOf(),
  @SerializedName("no_damage_from") val noDamageFrom: List<InfoResponse> = listOf(),
  @SerializedName("no_damage_to") val noDamageTo: List<InfoResponse> = listOf()
)