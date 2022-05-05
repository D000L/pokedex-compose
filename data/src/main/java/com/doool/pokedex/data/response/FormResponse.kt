package com.doool.pokedex.data.response

import androidx.annotation.Keep
import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.data.response.common.Names
import com.google.gson.annotations.SerializedName

@Keep
data class FormResponse(
    val name: String = "",
    @SerializedName("form_name") val formName: String = "",
    @SerializedName("form_names") val formNames: List<Names> = listOf(),
    @SerializedName("form_order")
    val formOrder: Int = 0,
    val id: Int = 0,
    @SerializedName("is_battle_only") val isBattleOnly: Boolean = false,
    @SerializedName("is_default") val isDefault: Boolean = false,
    @SerializedName("is_mega") val isMega: Boolean = false,
    val pokemon: InfoResponse = InfoResponse(),
    val types: List<TypeResponse> = listOf(),
    val order: Int = 0
)