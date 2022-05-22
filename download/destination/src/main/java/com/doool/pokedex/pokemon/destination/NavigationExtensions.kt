package com.doool.pokedex.pokemon.destination

import android.content.Context
import android.content.Intent
import com.doool.pokedex.download.feature.DownloadActivity

fun Context.goDownload() {
    startActivity(Intent(this, DownloadActivity::class.java))
}
