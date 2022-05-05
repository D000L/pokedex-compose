package com.doool.pokedex.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String = ""
) : Parcelable