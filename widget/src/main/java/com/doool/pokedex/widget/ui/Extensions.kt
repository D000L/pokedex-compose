package com.doool.pokedex.widget.ui

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager

suspend fun Context.getGlanceId(): GlanceId? {
    return GlanceAppWidgetManager(this).getGlanceIds(PokedexWidget::class.java).firstOrNull()
}