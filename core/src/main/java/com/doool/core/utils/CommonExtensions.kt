package com.doool.core.utils

fun String.capitalizeAndRemoveHyphen(): String {
    return split('-').joinToString(" ") {
        it.replaceFirstChar { it.uppercaseChar() }
    }
}
