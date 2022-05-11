package com.doool.pokedex.presentation.ui.pokemon_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.getData
import com.doool.pokedex.domain.isLoading
import com.doool.pokedex.domain.model.Condition
import com.doool.pokedex.domain.model.LocalizedInfo
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.presentation.ui.common.EvolutionType
import com.doool.pokedex.presentation.ui.pokemon_info.model.EvolutionListUIModel
import com.doool.pokedex.presentation.ui.widget.DarkPokeBall
import com.doool.pokedex.presentation.ui.widget.Space
import com.doool.pokedex.presentation.ui.widget.SpaceFill
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.localized

@Composable
fun EvolutionList(
    modifier: Modifier = Modifier,
    evolutionListUIState: LoadState<EvolutionListUIModel>,
    onClickPokemon: (String) -> Unit
) {
    Box {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            evolutionListUIState.getData()?.let { evolutionListUIModel ->
                if (evolutionListUIModel.evolutions.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = 140.dp),
                        text = "No Evolution",
                        style = MaterialTheme.typography.body1
                    )
                } else {
                    evolutionListUIModel.evolutions.forEach {
                        Evolution(chain = it, onClickPokemon = onClickPokemon)
                    }
                }
            }
        }

        if (evolutionListUIState.isLoading())
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
    }
}

@Composable
private fun Evolution(chain: PokemonEvolutionChain, onClickPokemon: (String) -> Unit) {
    Row {
        Pokemon(chain.from, onClickPokemon)
        SpaceFill()
        EvolutionTrigger(
            modifier = Modifier.align(Alignment.CenterVertically),
            condition = chain.condition
        )
        SpaceFill()
        Pokemon(chain.to, onClickPokemon)
    }
}

@Composable
private fun Pokemon(pokemonInfo: LocalizedInfo, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .height(120.dp)
            .clickable { onClick(pokemonInfo.name) },
        contentAlignment = Alignment.Center
    ) {
        PokemonBackground(modifier = Modifier.align(Alignment.Center))

        PokemonThumbnailAndName(
            imageUrl = pokemonInfo.imageUrl,
            name = pokemonInfo.names.localized.capitalizeAndRemoveHyphen()
        )
    }
}

@Composable
private fun PokemonBackground(modifier: Modifier = Modifier) {
    DarkPokeBall(
        modifier = modifier,
        size = 96.dp,
        translateOffset = DpOffset(x = 0.dp, y = -16.dp),
        rotate = 0f
    )
}

@Composable
private fun PokemonThumbnailAndName(imageUrl: String, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(76.dp),
            painter = rememberImagePainter(imageUrl),
            contentDescription = null
        )
        Space(height = 4.dp)
        Text(
            text = name,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun EvolutionTrigger(modifier: Modifier = Modifier, condition: Condition) {
    val evolutionType = EvolutionType.from(condition.trigger.name)

    Column(modifier) {
        when (evolutionType) {
            EvolutionType.LevelUp -> LevelEvolution(level = condition.minLevel)
            EvolutionType.Item -> ItemEvolution(name = condition.item?.name ?: "")
            EvolutionType.Trade -> TradeEvolution()
            EvolutionType.Shed -> {}
            EvolutionType.Other -> {}
            null -> {}
        }
    }
}

@Composable
private fun LevelEvolution(level: Int) {
    Text(text = "$level Level", style = MaterialTheme.typography.body2)
}

@Composable
private fun TradeEvolution() {
    Text(text = "Trade", style = MaterialTheme.typography.body2)
}

@Composable
private fun ItemEvolution(name: String) {
    Text(text = name.capitalizeAndRemoveHyphen(), style = MaterialTheme.typography.body2)
}
