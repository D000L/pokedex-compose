package com.doool.pokedex.presentation.utils

import android.content.Context
import android.content.Intent
import com.doool.pokedex.download.feature.DownloadActivity

fun Context.goDownload() {
    startActivity(Intent(this, DownloadActivity::class.java))
}
