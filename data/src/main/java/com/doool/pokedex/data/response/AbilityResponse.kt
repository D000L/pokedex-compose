package com.doool.pokedex.data.response

import com.doool.pokedex.data.response.common.EffectResponse
import com.doool.pokedex.data.response.common.FlavorText
import com.doool.pokedex.data.response.common.Names
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityResponse(
    val id: Int = 0,
    val name: String = "",
    val names: List<Names> = emptyList(),
    @SerialName("effect_entries") val effectEntries: List<EffectResponse> = emptyList(),
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorText> = emptyList()
)