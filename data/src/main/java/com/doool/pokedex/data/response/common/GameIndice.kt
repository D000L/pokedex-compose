package com.doool.pokedex.data.response.common

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GameIndice(
  @SerializedName("game_index") val gameIndex: Int = 0,
  val version: InfoResponse = InfoResponse()
)
