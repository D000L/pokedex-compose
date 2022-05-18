package com.doool.core.nav

import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType

fun NavArgumentBuilder.nullableType(navType: NavType<*>) {
    this.type = navType
    this.nullable = true
    this.defaultValue = null
}
