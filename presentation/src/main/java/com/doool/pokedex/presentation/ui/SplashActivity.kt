package com.doool.pokedex.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnPreDrawListener
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.doool.pokedex.presentation.utils.goDownload
import com.doool.pokedex.presentation.utils.goMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

  private val viewModel: SplashViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val content: View = findViewById(android.R.id.content)
    content.viewTreeObserver.addOnPreDrawListener(
      object : OnPreDrawListener {
        override fun onPreDraw(): Boolean {
          return if (viewModel.isReady) {
            content.viewTreeObserver.removeOnPreDrawListener(this)
            goNextActivity()
            true
          } else {
            false
          }
        }
      }
    )
  }

  private fun goNextActivity() {
    if (viewModel.isDownloaded) goMain() else goDownload()
    finish()
  }
}