package com.doool.pokedex.presentation.ui.main.pokemon.list

import androidx.compose.foundation.Image
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
import com.doool.pokedex.presentation.ui.main.LocalNavController
import com.doool.pokedex.presentation.ui.main.common.Pokeball
import com.doool.pokedex.presentation.ui.main.common.Space
import com.doool.pokedex.presentation.ui.main.common.TypeList
import com.doool.pokedex.presentation.ui.main.common.getBackgroundColor
import com.doool.pokedex.presentation.ui.main.pokemon.detail.PokemonInfoDestination
import com.doool.pokedex.presentation.utils.Process
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.clipBackground
import com.doool.pokedex.presentation.utils.defaultPlaceholder

@Composable
fun PokemonListScreen(
  pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {

  val pokemonList by pokemonListViewModel.pokemonList.collectAsState(initial = emptyList())

  Column(Modifier.padding(horizontal = 20.dp)) {
    PokemonList(pokemonListViewModel, pokemonList)
  }
}

@Composable
fun PokemonList(
  viewModel: PokemonListViewModel,
  list: List<String>
) {
  val navController = LocalNavController.current

  LazyColumn(
    contentPadding = PaddingValues(top = 20.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(list, key = { it }) {
      val pokemon by remember { viewModel.getPokemon(it) }.collectAsState()

      Pokemon(pokemonState = pokemon) {
        navController.navigate(PokemonInfoDestination.getRouteByName(it))
      }
    }
  }
}

@Preview
@Composable
fun PokemonPreview() {
  Pokemon(
    PokemonDetail(
      101,
      "electrode",
      14,
      54,
      "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/101.png",
      listOf(),
      listOf(Info("bug"), Info("fairy")),
      listOf()
    )
  ) {}
}

@Composable
private fun PokemonPlaceholder() {
  Box(Modifier.height(130.dp)) {
    Box(
      Modifier
        .align(Alignment.BottomStart)
        .fillMaxWidth()
        .height(96.dp)
        .defaultPlaceholder(true, RoundedCornerShape(10.dp))
    )
  }
}

@Composable
private fun Pokemon(pokemon: PokemonDetail, onClick: (String) -> Unit = {}) {
  Box(Modifier.height(130.dp)) {
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
              brush = Brush.verticalGradient(colors = listOf(color.copy(0.3f), color.copy(0.2f))),
              cornerRadius = CornerRadius(60f, 60f)
            )
          }
          drawContent()
        }
        .clipBackground(
          color = colorResource(id = pokemon.getBackgroundColor()),
          shape = RoundedCornerShape(10.dp)
        )
        .clickable { onClick(pokemon.name) }
    ) {
      Pokeball(180.dp, Alignment.CenterEnd, DpOffset(35.dp, 32.dp))
      PokemonSummary(Modifier.padding(top = 6.dp, start = 16.dp), pokemon)
    }
    Image(
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 10.dp)
        .requiredSize(120.dp),
      painter = rememberImagePainter(pokemon.image),
      contentDescription = null
    )
  }
}

@Composable
fun Pokemon(pokemonState: LoadState<PokemonDetail>, onClick: (String) -> Unit) {
  pokemonState.Process(onLoading = {
    PokemonPlaceholder()
  }, onComplete = {
    Pokemon(pokemon = it, onClick = onClick)
  })
}

@Composable
private fun PokemonSummary(modifier: Modifier = Modifier, pokemon: PokemonDetail) {
  Column(modifier) {
    Text(
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