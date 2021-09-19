package com.doool.pokedex.presentation

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import com.doool.pokedex.BuildConfig

fun Application.initCoil() {
    Coil.setImageLoader(
        ImageLoader.Builder(this).apply {
            if (BuildConfig.DEBUG) logger(DebugLogger())
        }.build()
    )
}