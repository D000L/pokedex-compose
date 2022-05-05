package com.doool.pokedex

import android.app.Application
import com.doool.pokedex.presentation.initCoil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokedexApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initCoil()
    }
}