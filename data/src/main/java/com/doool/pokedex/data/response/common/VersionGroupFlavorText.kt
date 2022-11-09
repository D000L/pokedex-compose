package com.doool.pokedex.data.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionGroupFlavorText(
    val language: InfoResponse = InfoResponse(),
    val text: String = "",
    @SerialName("version_group")
    val versionGroup: InfoResponse = InfoResponse()
)