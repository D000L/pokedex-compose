package com.doool.pokedex.presentation.ui.move_list


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.doool.pokedex.R
import com.doool.pokedex.presentation.LocalPokemonColor
import com.doool.pokedex.presentation.ui.widget.SpaceFill

@Composable
fun MoveHeaders(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.body2.copy(color = LocalPokemonColor.current)
        ) {
            Text(text = stringResource(R.string.move_name_label))
            SpaceFill()
            MoveHeader(title = R.string.move_power_label, width = 40.dp)
            MoveHeader(title = R.string.move_accurary_label, width = 40.dp)
            MoveHeader(title = R.string.move_power_point_label, width = 40.dp)
            MoveHeader(title = R.string.move_type_label, width = 80.dp)
        }
    }
}

@Composable
private fun MoveHeader(@StringRes title: Int, width: Dp) {
    Text(
        modifier = Modifier.width(width),
        textAlign = TextAlign.Center,
        text = stringResource(id = title)
    )
}
