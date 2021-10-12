package com.doool.pokedex.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
  val name: String = "",
  var url: String = ""
) : Parcelable