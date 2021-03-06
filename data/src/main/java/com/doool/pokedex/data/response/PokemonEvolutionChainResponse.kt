package com.doool.pokedex.data.response

import androidx.annotation.Keep
import com.doool.pokedex.data.response.common.InfoResponse
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonEvolutionChainResponse(
    @SerializedName("baby_trigger_item") val babyTriggerItem: Any? = null,
    val chain: Chain = Chain(),
    val id: Int = 0
)

@Keep
data class Chain(
    @SerializedName("evolution_details") val evolutionDetails: List<Any> = listOf(),
    @SerializedName("evolves_to") val evolvesTo: List<EvolvesTo> = listOf(),
    @SerializedName("is_baby") val isBaby: Boolean = false,
    val species: InfoResponse = InfoResponse()
)

@Keep
data class EvolvesTo(
    @SerializedName("evolution_details") val evolutionDetails: List<EvolutionDetail> = listOf(),
    @SerializedName("evolves_to") val evolvesTo: List<EvolvesTo> = listOf(),
    @SerializedName("is_baby") val isBaby: Boolean = false,
    val species: InfoResponse = InfoResponse()
)

@Keep
data class EvolutionDetail(
    val gender: Any? = null,
    @SerializedName("held_item") val heldItem: Any? = null,
    val item: InfoResponse? = null,
    @SerializedName("known_move") val knownMove: Any? = null,
    @SerializedName("known_move_type") val knownMoveType: Any? = null,
    val location: Any? = null,
    @SerializedName("min_affection") val minAffection: Any? = null,
    @SerializedName("min_beauty") val minBeauty: Any? = null,
    @SerializedName("min_happiness") val minHappiness: Any? = null,
    @SerializedName("min_level") val minLevel: Int = 0,
    @SerializedName("needs_overworld_rain") val needsOverworldRain: Boolean = false,
    @SerializedName("party_species") val partySpecies: Any? = null,
    @SerializedName("party_type") val partyType: Any? = null,
    @SerializedName("relative_physical_stats") val relativePhysicalStats: Int = 0,
    @SerializedName("time_of_day") val timeOfDay: String = "",
    @SerializedName("trade_species") val tradeSpecies: Any? = null,
    val trigger: InfoResponse = InfoResponse(),
    @SerializedName("turn_upside_down") val turnUpsideDown: Boolean = false
)