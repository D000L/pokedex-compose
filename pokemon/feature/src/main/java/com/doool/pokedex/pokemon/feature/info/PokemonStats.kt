package com.doool.pokedex.pokemon.feature.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.doool.pokedex.core.LocalPokemonColor
import com.doool.pokedex.core.common.PokemonType
import com.doool.pokedex.core.common.StatType
import com.doool.pokedex.core.widget.Space
import com.doool.pokedex.core.widget.Type
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.getData
import com.doool.pokedex.domain.isLoading
import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.model.Stat
import com.doool.pokedex.pokemon.feature.info.model.StatsUIModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun PokemonStats(
    modifier: Modifier = Modifier,
    statsUIState: LoadState<StatsUIModel>
) {
    Box {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            statsUIState.getData()?.let { statsUIModel ->
                BaseStats(stats = statsUIModel.stats)
                Space(height = 12.dp)
                TypeDefenses(damageRelations = statsUIModel.damageRelations)
            }
        }
        if (statsUIState.isLoading())
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun BaseStats(stats: List<Stat>) {
    CommonSubTitle("Base Stats")

    stats.forEach { stat ->
        Stat(
            stat = StatType.from(stat.name),
            color = LocalPokemonColor.current,
            amount = stat.amount
        )
    }
}

@Composable
private fun Stat(stat: StatType, color: Color, amount: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stat.statName,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.width(54.dp)
        )
        Space(width = 6.dp)

        Text(
            text = amount.toString(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.width(30.dp),
            textAlign = TextAlign.End
        )
        Space(width = 12.dp)

        StatBar(amount = amount, color = color)
    }
}

@Composable
private fun StatBar(amount: Int, color: Color) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        val fraction = amount / 255.0f

        Box(
            Modifier
                .fillMaxWidth(fraction)
                .height(4.dp)
                .background(color = color, shape = RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun TypeDefenses(damageRelations: List<Damage>) {
    CommonSubTitle("Type Defenses")
    Space(height = 2.dp)

    FlowRow {
        val damageMap = damageRelations.associateBy { it.type }

        PokemonType.values().forEach {
            val typeName = it.name.lowercase()
            val damageRelation = damageMap[typeName]?.amount ?: 1f

            TypeAndDamageRelation(type = it, damageRelation = damageRelation)
        }
    }
}

@Composable
private fun TypeAndDamageRelation(type: PokemonType, damageRelation: Float) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .height(46.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Type(type = type, size = 24.dp)
        Space(height = 4.dp)
        DamageRelation(amount = damageRelation)
    }
}

@Composable
private fun DamageRelation(amount: Float) {
    if (amount != 1f) {
        val amountString = when (amount) {
            0f -> "0"
            2f -> "2"
            0.5f -> "½"
            0.25f -> "¼"
            else -> amount.toString()
        }

        Text(
            text = amountString,
            style = MaterialTheme.typography.body1
        )
    }
}
