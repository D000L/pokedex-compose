package com.doool.pokedex.presentation.ui.main.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.doool.pokedex.R

@Composable
fun BoxScope.Pokeball(size : Dp, alignment: Alignment, translateOffset : DpOffset, rotate :Float= 160f){
  val density = LocalDensity.current
  
  Image(modifier = Modifier.requiredSize(size)
    .alpha(0.15f)
    .align(alignment)
    .drawWithContent {
      withTransform({
        translate(left = density.run { translateOffset.x.toPx() } , top = density.run { translateOffset.y.toPx() })
        rotate(rotate)
      }) {
        this@drawWithContent.drawContent()
      }
    },painter = painterResource(id = R.drawable.ic_pokeball), contentDescription = null)
}