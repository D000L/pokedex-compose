package com.doool.pokedex.data.response

import com.doool.pokedex.data.response.common.InfoResponse
import com.doool.pokedex.data.response.common.Names
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FormResponse(
    val name: String = "",
    @SerialName("form_name") val formName: String = "",
    @SerialName("form_names") val formNames: List<Names> = listOf(),
    @SerialName("form_order")
    val formOrder: Int = 0,
    val id: Int = 0,
    @SerialName("is_battle_only") val isBattleOnly: Boolean = false,
    @SerialName("is_default") val isDefault: Boolean = false,
    @SerialName("is_mega") val isMega: Boolean = false,
    val pokemon: InfoResponse = InfoResponse(),
    val types: List<TypeResponse> = listOf(),
    val order: Int = 0
)