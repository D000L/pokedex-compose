package com.doool.pokedex.data.response

import com.doool.pokedex.data.response.common.EffectResponse
import com.doool.pokedex.data.response.common.GameIndice
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.data.response.common.Names
import com.doool.pokedex.data.response.common.Sprites
import com.doool.pokedex.data.response.common.UnusedField
import com.doool.pokedex.data.response.common.VersionGroupFlavorText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemResponse(
    val attributes: List<InfoResponse> = listOf(),
    @SerialName("baby_trigger_for") val babyTriggerFor: UnusedField = UnusedField(),
    val category: InfoResponse = InfoResponse(),
    val cost: Int = 0,
    @SerialName("effect_entries") val effectEntries: List<EffectResponse> = listOf(),
    @SerialName("flavor_text_entries") val flavorTextEntries: List<VersionGroupFlavorText> = listOf(),
    @SerialName("fling_effect") val flingEffect: UnusedField = UnusedField(),
    @SerialName("fling_power") val flingPower: UnusedField = UnusedField(),
    @SerialName("game_indices") val gameIndices: List<GameIndice> = listOf(),
    @SerialName("held_by_pokemon") val heldByPokemon: List<UnusedField> = listOf(),
    val id: Int = 0,
    val machines: List<UnusedField> = listOf(),
    val name: String = "",
    val names: List<Names> = listOf(),
    val sprites: Sprites = Sprites()
)