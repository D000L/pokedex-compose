package com.doool.pokedex.presentation.ui.common

import com.doool.pokedex.R

enum class MoveCategoryColor(val colorRes: Int) {
    Status(R.color.background_normal),
    Physical(R.color.background_fighting),
    Special(R.color.background_water);

    companion object {
        fun from(name: String) = values().firstOrNull {
            it.name.lowercase() == name.lowercase()
        } ?: Special
    }
}