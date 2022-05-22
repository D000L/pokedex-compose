package com.doool.pokedex.core.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.doool.pokedex.core.utils.getItemTopOffset
import com.doool.pokedex.navigation.LocalNavController

val TOOLBAR_HEIGHT = 56.dp

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.stickyAppBar(
    state: LazyListState,
    @StringRes title: Int,
    backButtonColor: Color = Color.Black
) {
    stickyHeader {
        val density = LocalDensity.current
        val showHeaderDivider by derivedStateOf {
            state.getItemTopOffset(1) < density.run { TOOLBAR_HEIGHT.toPx() }
        }

        DefaultAppBar(
            title = stringResource(id = title),
            backButtonColor = backButtonColor,
            showDivider = showHeaderDivider
        )
    }
}

@Composable
fun DefaultAppBar(
    title: String = "",
    backButtonColor: Color = Color.Black,
    showDivider: Boolean = true
) {
    val modifier =
        if (showDivider) {
            Modifier
                .shadow(8.dp)
                .background(Color.White)
        } else Modifier

    Box(
        modifier = modifier
            .height(TOOLBAR_HEIGHT)
            .fillMaxWidth()
    ) {
        BackButton(modifier = Modifier.align(Alignment.CenterStart), color = backButtonColor)

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = MaterialTheme.typography.h4
        )

        if (showDivider) {
            Divider(modifier = Modifier.align(Alignment.BottomCenter), thickness = 0.5.dp)
        }
    }
}

@Composable
private fun BackButton(modifier: Modifier = Modifier, color: Color) {
    val navController = LocalNavController.current

    IconButton(
        modifier = modifier,
        onClick = navController::navigateUp
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = color)
    }
}
