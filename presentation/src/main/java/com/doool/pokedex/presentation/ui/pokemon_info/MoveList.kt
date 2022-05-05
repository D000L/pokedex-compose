package com.doool.pokedex.presentation.ui.pokemon_info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.Effect
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.presentation.LocalPokemonColor
import com.doool.pokedex.presentation.Process
import com.doool.pokedex.presentation.ui.common.PokemonType
import com.doool.pokedex.presentation.ui.widget.SpaceFill
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.defaultPlaceholder
import com.doool.pokedex.presentation.utils.localized

@Composable
fun MoveHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Move Name",
            style = MaterialTheme.typography.body2,
            color = LocalPokemonColor.current
        )
        SpaceFill()
        MoveHeaderItem("Power", 40.dp)
        MoveHeaderItem("Acc", 40.dp)
        MoveHeaderItem("PP", 40.dp)
        MoveHeaderItem("Type", 80.dp)
    }
}

@Composable
private fun MoveHeaderItem(title: String, width: Dp) {
    Text(
        modifier = Modifier.width(width),
        textAlign = TextAlign.Center,
        text = title,
        style = MaterialTheme.typography.body2,
        color = LocalPokemonColor.current
    )
}

@Composable
private fun MoveItem(title: String, width: Dp) {
    Text(
        modifier = Modifier.width(width),
        textAlign = TextAlign.Center,
        text = title,
        style = MaterialTheme.typography.body1
    )
}

@Composable
private fun MovePlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .defaultPlaceholder(true, RoundedCornerShape(2.dp))
    )
}

@Composable
private fun Move(move: PokemonMove, onItemClicked: (String) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(move.name) }
            .padding(horizontal = 20.dp)
            .height(42.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val type = remember(move.type.name) { PokemonType.from(move.type.name) }

        Text(
            modifier = Modifier.weight(1f),
            text = move.names.localized.capitalizeAndRemoveHyphen(),
            style = MaterialTheme.typography.body1
        )
        MoveItem(move.power.toString(), 40.dp)
        MoveItem(move.accuracy.toString(), 40.dp)
        MoveItem(move.pp.toString(), 40.dp)
        Box(
            Modifier
                .size(80.dp, 24.dp)
                .shadow(4.dp, CircleShape)
                .background(colorResource(id = type.typeColorResId), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = type.name)
        }
    }
}

@Composable
fun Move(moveState: LoadState<PokemonMove>, onItemClicked: (String) -> Unit = {}) {
    moveState.Process(onLoading = {
        MovePlaceholder()
    }, onComplete = { move ->
        Move(move, onItemClicked)
    })
}

@Composable
@Preview
fun MovePreview() {
    Move(
        PokemonMove(
            name = "Mega Punch",
            id = 1,
            accuracy = 100,
            damageClass = com.doool.pokedex.domain.model.Info(
                name = "physical"
            ),
            flavorTextEntries = listOf(LocalizedString("A ramming attack that may cause flinching.")),
            effectEntries = Effect(
                effect = "Inflicts regular damage.  Has a 30% chance to make the target flinch.",
                shortEffect = "Has a 30% chance to make the target flinch.",
                effectChance = 30
            ),
            power = 70, pp = 15, type = com.doool.pokedex.domain.model.Info(name = "normal")
        )
    )
}