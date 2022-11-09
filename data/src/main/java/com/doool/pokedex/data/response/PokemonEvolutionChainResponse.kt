package com.doool.pokedex.data.response

import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.data.response.common.UnusedField
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonEvolutionChainResponse(
    @SerialName("baby_trigger_item") val babyTriggerItem: UnusedField? = null,
    val chain: Chain = Chain(),
    val id: Int = 0
)

@Serializable
data class Chain(
    @SerialName("evolution_details") val evolutionDetails: List<UnusedField> = listOf(),
    @SerialName("evolves_to") val evolvesTo: List<EvolvesTo> = listOf(),
    @SerialName("is_baby") val isBaby: Boolean = false,
    val species: InfoResponse = InfoResponse()
)

@Serializable
data class EvolvesTo(
    @SerialName("evolution_details") val evolutionDetails: List<EvolutionDetail> = listOf(),
    @SerialName("evolves_to") val evolvesTo: List<EvolvesTo> = listOf(),
    @SerialName("is_baby") val isBaby: Boolean = false,
    val species: InfoResponse = InfoResponse()
)

@Serializable
data class EvolutionDetail(
    val gender: UnusedField? = null,
    @SerialName("held_item") val heldItem: UnusedField? = null,
    val item: InfoResponse? = null,
    @SerialName("known_move") val knownMove: UnusedField? = null,
    @SerialName("known_move_type") val knownMoveType: UnusedField? = null,
    val location: UnusedField? = null,
    @SerialName("min_affection") val minAffection: UnusedField? = null,
    @SerialName("min_beauty") val minBeauty: UnusedField? = null,
    @SerialName("min_happiness") val minHappiness: UnusedField? = null,
    @SerialName("min_level") val minLevel: Int = 0,
    @SerialName("needs_overworld_rain") val needsOverworldRain: Boolean = false,
    @SerialName("party_species") val partySpecies: UnusedField? = null,
    @SerialName("party_type") val partyType: UnusedField? = null,
    @SerialName("relative_physical_stats") val relativePhysicalStats: Int = 0,
    @SerialName("time_of_day") val timeOfDay: String = "",
    @SerialName("trade_species") val tradeSpecies: UnusedField? = null,
    val trigger: InfoResponse = InfoResponse(),
    @SerialName("turn_upside_down") val turnUpsideDown: Boolean = false
)