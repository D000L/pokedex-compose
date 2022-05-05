package com.doool.pokedex.presentation.utils

fun String.capitalizeAndRemoveHyphen(): String {
    return split('-').map {
        it.replaceFirstChar { it.uppercaseChar() }
    }.joinToString(" ")
}