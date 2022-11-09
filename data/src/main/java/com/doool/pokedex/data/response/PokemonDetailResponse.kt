package com.doool.pokedex.data.response

import com.doool.pokedex.data.response.common.GameIndice
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.data.response.common.Sprites
import com.doool.pokedex.data.response.common.UnusedField
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    val abilities: List<AbilityInfoResponse> = listOf(),
    @SerialName("base_experience") val baseExperience: Int = 0,
    val forms: List<InfoResponse> = listOf(),
    @SerialName("game_indices") val gameIndices: List<GameIndice> = listOf(),
    val height: Int = 0,
    @SerialName("held_items") val heldItems: List<UnusedField> = listOf(),
    val id: Int = 0,
    @SerialName("is_default") val isDefault: Boolean = false,
    @SerialName("location_area_encounters") val locationAreaEncounters: String = "",
    val moves: List<MoveResponse> = listOf(),
    val name: String = "",
    val order: Int = 0,
    @SerialName("past_types") val pastTypes: List<UnusedField> = listOf(),
    val species: InfoResponse = InfoResponse(),
    val sprites: Sprites = Sprites(),
    val stats: List<StatResponse> = listOf(),
    val types: List<TypeResponse> = listOf(),
    val weight: Int = 0
)

@Serializable
data class AbilityInfoResponse(
    val ability: InfoResponse = InfoResponse(),
    @SerialName("is_hidden") val isHidden: Boolean = false,
    val slot: Int = 0
)

@Serializable
data class MoveResponse(
    val move: InfoResponse = InfoResponse(),
    @SerialName("version_group_details") val versionGroupDetails: List<VersionGroupDetailResponse> = listOf()
)

@Serializable
data class VersionGroupDetailResponse(
    @SerialName("level_learned_at") val levelLearnedAt: Int = 0,
    @SerialName("move_learn_method") val moveLearnMethod: InfoResponse = InfoResponse(),
    @SerialName("version_group") val versionGroup: InfoResponse = InfoResponse()
)

@Serializable
data class StatResponse(
    @SerialName("base_stat") val baseStat: Int = 0,
    val effort: Int = 0,
    val stat: InfoResponse = InfoResponse()
)

@Serializable
data class TypeResponse(
    val slot: Int = 0,
    val type: InfoResponse = InfoResponse()
)