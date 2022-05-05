package com.doool.pokedex.widget.ui

import android.content.Context

interface WidgetActions {
    suspend fun loadPokemon(context: Context, id: Int)
}
