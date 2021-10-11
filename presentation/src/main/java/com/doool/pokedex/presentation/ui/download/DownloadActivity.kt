package com.doool.pokedex.presentation.ui.download

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.doool.pokedex.presentation.ui.main.MainActivity
import com.doool.pokedex.presentation.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      PokedexTheme {
        DownloadScreen(){
          startActivity(Intent(this, MainActivity::class.java))
        }
      }
    }
  }
}