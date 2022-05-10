package com.doool.pokedex.presentation.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.doool.pokedex.R

@Preview
@Composable
fun PreviewPokeball() {
    Box {
        PokeBall(
            modifier = Modifier.align(Alignment.Center),
            size = 60.dp,
            translateOffset = DpOffset(x = 0.dp, y = -12.dp)
        )
    }
}

@Composable
fun PokeBall(
    modifier: Modifier = Modifier,
    size: Dp,
    translateOffset: DpOffset = DpOffset.Zero,
    rotate: Float = 160f,
) {
    Image(
        modifier = modifier
            .requiredSize(size)
            .alpha(0.15f)
            .offset(translateOffset.x, translateOffset.y)
            .rotate(rotate),
        painter = painterResource(id = R.drawable.ic_pokeball),
        contentDescription = null
    )
}

@Composable
fun DarkPokeBall(
    modifier: Modifier = Modifier,
    size: Dp,
    translateOffset: DpOffset = DpOffset.Zero,
    rotate: Float = 160f,
) {
    Image(
        modifier = Modifier
            .requiredSize(size)
            .offset(translateOffset.x, translateOffset.y)
            .rotate(rotate)
            .clip(CircleShape)
            .then(modifier),
        colorFilter = ColorFilter.tint(
            color = colorResource(id = R.color.gray),
            blendMode = BlendMode.DstIn
        ),
        painter = painterResource(id = R.drawable.ic_pokeball),
        contentDescription = null
    )
}