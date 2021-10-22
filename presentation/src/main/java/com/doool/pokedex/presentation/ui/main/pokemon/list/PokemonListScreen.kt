package com.doool.pokedex.presentation.ui.main.pokemon.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.withLoadState
import com.doool.pokedex.presentation.ui.main.common.Pokeball
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.ui.main.common.TypeList
import com.doool.pokedex.presentation.ui.main.common.getBackgroundColor
import com.doool.pokedex.presentation.utils.Process
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import kotlinx.coroutines.flow.Flow

@Composable
fun PokemonListScreen(
  pokemonListViewModel: PokemonListViewModel = hiltViewModel(),
  navigateDetail: (String) -> Unit
) {

  val pokemonList by pokemonListViewModel.pokemonList.collectAsState(initial = emptyList())

  Column(Modifier.padding(horizontal = 20.dp)) {
    PokemonList(list = pokemonList, pokemonListViewModel::getPokemon, navigateDetail)
  }
}

@Composable
fun PokemonList(
  list: List<String>,
  getPokemon: (String) -> Flow<PokemonDetail>,
  navigateDetail: (String) -> Unit
) {
  LazyColumn(
    contentPadding = PaddingValues(top = 20.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(list) {
      val pokemon by remember { getPokemon(it).withLoadState() }.collectAsState(initial = LoadState.Loading)
      pokemon.Process(onLoading = {
        Box(
          Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
        )
      }, onComplete = {
        Pokemon(pokemon = it, onClick = navigateDetail)
      })
    }
  }
}

@Preview
@Composable
fun PokemonPreview() {
  Pokemon(PokemonDetail(
    101,
    "electrode",
    14,
    54,
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/101.png",
    listOf(),
    listOf(Info("bug"), Info("fairy")),
    listOf()
  ), {})
}

@Composable
fun Pokemon(pokemon: PokemonDetail, onClick: (String) -> Unit) {
  Box(Modifier.clickable { onClick(pokemon.name) }) {
    val density = LocalDensity.current
    val color = colorResource(id = pokemon.getBackgroundColor())
    Box(
      Modifier
        .align(Alignment.BottomStart)
        .fillMaxWidth()
        .height(96.dp)
        .drawWithContent {
          withTransform({
            translate(left = density.run { 5.dp.toPx() }, top = density.run { 10.dp.toPx() })
            scale(1.00f)
          }) {
            drawRoundRect(
              brush = Brush.verticalGradient(
                colors = listOf(
                  color.copy(0.3f),
                  color.copy(0.2f)
                )
              ), cornerRadius = CornerRadius(60f, 60f)
            )
          }
          drawContent()
        }
        .background(
          color = colorResource(id = pokemon.getBackgroundColor()),
          shape = RoundedCornerShape(10.dp)
        )
    ) {
      Box {
        Pokeball(180.dp, Alignment.CenterEnd, DpOffset(35.dp, 32.dp))
        Row(
          modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxSize(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              modifier = Modifier
                .padding(end = 10.dp),
              text = "#%03d".format(pokemon.id),
              fontSize = 12.sp,
              color = Color.White.copy(alpha = 0.4f),
              fontWeight = FontWeight.Bold
            )
            Text(
              text = pokemon.name.capitalizeAndRemoveHyphen(),
              color = Color.White,
              style = MaterialTheme.typography.h3
            )
            Space(height = 2.dp)
            TypeList(types = pokemon.types)
          }
        }
      }
    }
    PokemonThumbnail(
      Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 10.dp), pokemon.image
    )
  }
}

@Composable
fun PokemonThumbnail(modifier: Modifier = Modifier, url: String) {
  Image(
    modifier = modifier
      .requiredSize(120.dp),
    painter = rememberImagePainter(url),
    contentDescription = null
  )
}