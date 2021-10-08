package com.doool.pokedex.presentation.utils

import android.content.Intent
import androidx.activity.ComponentActivity
import com.doool.pokedex.presentation.ui.download.DownloadActivity
import com.doool.pokedex.presentation.ui.main.MainActivity

fun ComponentActivity.goMain() {
  startActivity(Intent(this, MainActivity::class.java))
}

fun ComponentActivity.goDownload() {
  startActivity(Intent(this, DownloadActivity::class.java))
}