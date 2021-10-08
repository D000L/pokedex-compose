package com.doool.pokedex.presentation.ui.download

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.doool.pokedex.presentation.ui.download.DownloadScreen
import com.doool.pokedex.presentation.ui.main.MainActivity
import com.doool.pokedex.presentation.ui.main.detail.DetailScreen
import com.doool.pokedex.presentation.ui.main.news.NewsScreen
import com.doool.pokedex.presentation.ui.main.pokemon.PokemonScreen
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