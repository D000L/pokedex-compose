package com.doool.pokedex.data.response.common

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VersionGroupFlavorText(
  val language: InfoResponse = InfoResponse(),
  val text: String = "",
  @SerializedName("version_group")
  val versionGroup: InfoResponse = InfoResponse()
)