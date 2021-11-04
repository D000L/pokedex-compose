package com.doool.pokedex.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.doool.pokedex.R

// Set of Material typography styles to start with
val Typography = Typography(
  defaultFontFamily = FontFamily(
    Font(R.font.sfpro_regular, FontWeight.Light),
    Font(R.font.sfpro_medium, FontWeight.Normal),
    Font(R.font.sfpro_bold, FontWeight.Bold)
  ),
  h1 = TextStyle(
    fontSize = 32.sp,
    fontWeight = FontWeight.Bold
  ),
  h2 = TextStyle(
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold
  ),
  h3 = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.Normal
  ),
  h4 = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Normal
  ),
  subtitle1 = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Normal
  ),
  subtitle2 = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal
  ),
  body1 = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.Light,
    color = Black
  ),
  body2 = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    color = Gray
  )
)