package com.doool.pokedex.data.response

import com.doool.pokedex.data.response.common.EffectResponse
import com.doool.pokedex.data.response.common.FlavorText
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.data.response.common.Names
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonMoveResponse(
    val id: Int = 0,
    val name: String = "",
    val accuracy: Int = 0,
    @SerialName("effect_chance") val effectChance: Int = 0,
    @SerialName("damage_class") val damageClass: InfoResponse? = null,
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorText> = emptyList(),
    @SerialName("effect_entries") val effectEntries: List<EffectResponse> = emptyList(),
    @SerialName("learned_by_pokemon") val learnedPokemon: List<InfoResponse> = emptyList(),
    val machines: List<MachinesResponse> = emptyList(),
    val generation: InfoResponse,
    val names: List<Names> = emptyList(),
    val power: Int = 0,
    val pp: Int = 0,
    val type: InfoResponse = InfoResponse()
)

@Serializable
data class MachinesResponse(
    val machine: InfoResponse,
    @SerialName("version_group") val versionGroup: InfoResponse = InfoResponse()
)