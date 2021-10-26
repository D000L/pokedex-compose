package com.doool.pokedex.presentation.utils

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import com.doool.pokedex.presentation.ui.download.DownloadActivity
import com.doool.pokedex.presentation.ui.main.MainActivity
import com.doool.pokedex.presentation.ui.setting.SettingActivity

fun Context.goMain() {
  startActivity(Intent(this, MainActivity::class.java))
}

fun Context.goDownload() {
  startActivity(Intent(this, DownloadActivity::class.java))
}

fun Context.goSetting(){
  startActivity(Intent(this, SettingActivity::class.java))
}