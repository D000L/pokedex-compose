package com.doool.pokedex.data.response

import com.doool.pokedex.data.response.common.InfoResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeResistanceResponse(
    @SerialName("damage_relations") val damageRelations: DamageRelationsResponse = DamageRelationsResponse(),
    val id: Int = 0,
    val name: String = ""
)

@Serializable
data class DamageRelationsResponse(
    @SerialName("double_damage_from") val doubleDamageFrom: List<InfoResponse> = listOf(),
    @SerialName("double_damage_to") val doubleDamageTo: List<InfoResponse> = listOf(),
    @SerialName("half_damage_from") val halfDamageFrom: List<InfoResponse> = listOf(),
    @SerialName("half_damage_to") val halfDamageTo: List<InfoResponse> = listOf(),
    @SerialName("no_damage_from") val noDamageFrom: List<InfoResponse> = listOf(),
    @SerialName("no_damage_to") val noDamageTo: List<InfoResponse> = listOf()
)