package com.doool.pokedex.data.response

import androidx.annotation.Keep
import com.doool.pokedex.data.response.common.EffectResponse
import com.doool.pokedex.data.response.common.FlavorText
import com.doool.pokedex.data.response.common.Names
import com.google.gson.annotations.SerializedName

@Keep
data class AbilityResponse(
    val id: Int = 0,
    val name: String = "",
    val names: List<Names> = emptyList(),
    @SerializedName("effect_entries") val effectEntries: List<EffectResponse> = emptyList(),
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorText> = emptyList()
)