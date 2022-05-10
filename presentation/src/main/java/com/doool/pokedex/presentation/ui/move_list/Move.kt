package com.doool.pokedex.presentation.ui.move_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.presentation.Process
import com.doool.pokedex.presentation.ui.common.PokemonType
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.defaultPlaceholder
import com.doool.pokedex.presentation.utils.localized

@Composable
fun Move(moveState: LoadState<PokemonMove>, onItemClicked: (String) -> Unit = {}) {
    moveState.Process(
        onLoading = { MovePlaceholder() },
        onComplete = { move -> Move(move, onItemClicked) }
    )
}

@Composable
private fun MovePlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MoveHeight)
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .defaultPlaceholder(visible = true, shape = RoundedCornerShape(2.dp))
    )
}

@Composable
private fun Move(move: PokemonMove, onItemClicked: (String) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(move.name) }
            .padding(horizontal = 20.dp)
            .height(MoveHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = move.names.localized.capitalizeAndRemoveHyphen(),
            style = MaterialTheme.typography.body1
        )

        MoveStatus(status = move.power, width = 40.dp)
        MoveStatus(status = move.accuracy, width = 40.dp)
        MoveStatus(status = move.pp, width = 40.dp)

        MoveType(type = PokemonType.from(move.type.name))
    }
}

@Composable
private fun MoveStatus(status: Int, width: Dp) {
    Text(
        modifier = Modifier.width(width),
        textAlign = TextAlign.Center,
        text = status.toString(),
        style = MaterialTheme.typography.body1
    )
}

@Composable
private fun MoveType(type: PokemonType) {
    Box(
        Modifier
            .size(width = 80.dp, height = 24.dp)
            .shadow(elevation = 4.dp, shape = CircleShape)
            .background(color = colorResource(id = type.typeColorResId), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = type.name)
    }
}

@Composable
@Preview
private fun MovePreview() {
    Move(
        PokemonMove(
            name = "Mega Punch",
            id = 1,
            accuracy = 100,
            damageClass = Info(
                name = "physical"
            ),
            flavorTextEntries = listOf(LocalizedString("A ramming attack that may cause flinching.")),
            effectEntries = Effect(
                effect = "Inflicts regular damage.  Has a 30% chance to make the target flinch.",
                shortEffect = "Has a 30% chance to make the target flinch.",
                effectChance = 30
            ),
            power = 70, pp = 15, type = Info(name = "normal")
        )
    )
}

private val MoveHeight = 42.dp
