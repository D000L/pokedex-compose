package com.doool.pokedex.presentation.utils

fun String.capitalizeAndRemoveHyphen(): String {
    return split('-').joinToString(" ") {
        it.replaceFirstChar { it.uppercaseChar() }
    }
}
