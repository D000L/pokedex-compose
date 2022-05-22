package com.doool.pokedex.pokemon.feature.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.doool.pokedex.core.Process
import com.doool.pokedex.core.extensions.getBackgroundColor
import com.doool.pokedex.core.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.core.utils.clipBackground
import com.doool.pokedex.core.utils.defaultPlaceholder
import com.doool.pokedex.core.utils.localized
import com.doool.pokedex.core.widget.PokeBall
import com.doool.pokedex.core.widget.Space
import com.doool.pokedex.core.widget.TypeList
import com.doool.pokedex.core.widget.stickyAppBar
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.pokemon.destination.PokemonInfoDestination
import com.doool.pokedex.pokemon.feature.R

@Composable
fun PokemonListScreen(
    pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by pokemonListViewModel.pokemonList.observeAsState(initial = emptyList())

    if (pokemonList.isNotEmpty()) PokemonList(pokemonListViewModel, pokemonList)

    LaunchedEffect(pokemonList) {
        if (pokemonList.isEmpty()) pokemonListViewModel.loadPokemonList()
    }
}

@Composable
private fun PokemonList(
    viewModel: PokemonListViewModel,
    list: List<Pokemon>
) {
    val navController = com.doool.pokedex.navigation.LocalNavController.current
    val state = rememberLazyListState()

    LazyColumn(
        state = state,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stickyAppBar(state = state, title = R.string.pokemon_title)

        items(list, key = { it.id }) {
            val itemState by remember { viewModel.getItemState(it.name) }.collectAsState()

            Pokemon(modifier = Modifier.padding(horizontal = 20.dp), item = itemState) {
                navController.navigate(PokemonInfoDestination.getRouteByName(it))
            }
        }
    }
}

@Preview
@Composable
private fun PokemonPreview() {
    Pokemon(
        item = PokemonListItem(
            101,
            "electrode",
            emptyList(),
            emptyList(),
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/101.png",
            listOf(Info("bug"), Info("fairy")),
        )
    ) {}
}

@Composable
private fun PokemonPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier.height(130.dp)) {
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
private fun Pokemon(
    modifier: Modifier = Modifier,
    item: PokemonListItem,
    onClick: (String) -> Unit = {}
) {
    Box(modifier.height(130.dp)) {
        val density = LocalDensity.current
        val color = colorResource(id = item.types.getBackgroundColor())

        Box(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(96.dp)
                .drawWithContent {
                    withTransform({
                        translate(
                            left = density.run { 5.dp.toPx() },
                            top = density.run { 10.dp.toPx() })
                        scale(1.00f)
                    }) {
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    color.copy(0.3f),
                                    color.copy(0.2f)
                                )
                            ),
                            cornerRadius = CornerRadius(60f, 60f)
                        )
                    }
                    drawContent()
                }
                .clipBackground(
                    color = color,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable { onClick(item.name) }
        ) {
            PokeBall(
                modifier = Modifier.align(Alignment.CenterEnd),
                size = 180.dp,
                translateOffset = DpOffset(35.dp, 32.dp)
            )
            PokemonSummary(Modifier.padding(top = 6.dp, start = 16.dp), item)
        }
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
                .requiredSize(120.dp),
            painter = rememberImagePainter(item.image),
            contentDescription = null
        )
    }
}

@Composable
private fun Pokemon(
    modifier: Modifier = Modifier,
    item: LoadState<PokemonListItem>,
    onClick: (String) -> Unit
) {
    item.Process(onLoading = {
        PokemonPlaceholder(modifier)
    }, onComplete = {
        Pokemon(modifier = modifier, item = it, onClick = onClick)
    })
}

@Composable
private fun PokemonSummary(modifier: Modifier = Modifier, item: PokemonListItem) {
    Column(modifier) {
        Text(
            text = "#%03d".format(item.id),
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.4f),
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = item.names.localized.capitalizeAndRemoveHyphen(),
                color = Color.White,
                style = MaterialTheme.typography.h3
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = item.formNames.localized.capitalizeAndRemoveHyphen(),
                color = Color.White.copy(0.7f),
                style = MaterialTheme.typography.body2
            )
        }
        Space(height = 2.dp)
        TypeList(types = item.types)
    }
}
