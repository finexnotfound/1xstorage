package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

// Smooth iOS San Francisco / Inter Font Family
val IosFontFamily = FontFamily(
  Font(R.font.inter, FontWeight.Normal),
  Font(R.font.inter, FontWeight.Medium),
  Font(R.font.inter, FontWeight.SemiBold),
  Font(R.font.inter, FontWeight.Bold)
)

// Smooth iOS Bold & Italic Typography for 1x Storage
val Typography = Typography(
  displayLarge = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 34.sp,
    lineHeight = 40.sp,
    letterSpacing = (-0.4).sp,
    color = PureWhite
  ),
  displayMedium = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 28.sp,
    lineHeight = 34.sp,
    letterSpacing = (-0.3).sp,
    color = PureWhite
  ),
  headlineLarge = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 24.sp,
    lineHeight = 30.sp,
    letterSpacing = (-0.2).sp,
    color = PureWhite
  ),
  headlineMedium = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 20.sp,
    lineHeight = 26.sp,
    letterSpacing = (-0.2).sp,
    color = PureWhite
  ),
  titleLarge = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = (-0.1).sp,
    color = PureWhite
  ),
  titleMedium = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontStyle = FontStyle.Italic,
    fontSize = 15.sp,
    lineHeight = 20.sp,
    letterSpacing = (-0.1).sp,
    color = PureWhite
  ),
  bodyLarge = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Normal,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.1).sp,
    color = PureWhite
  ),
  bodyMedium = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Normal,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    letterSpacing = (-0.05).sp,
    color = White75
  ),
  labelLarge = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp,
    color = PureWhite
  ),
  labelMedium = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontStyle = FontStyle.Italic,
    fontSize = 11.sp,
    lineHeight = 15.sp,
    letterSpacing = 0.sp,
    color = White75
  ),
  labelSmall = TextStyle(
    fontFamily = IosFontFamily,
    fontWeight = FontWeight.Medium,
    fontStyle = FontStyle.Italic,
    fontSize = 10.sp,
    lineHeight = 13.sp,
    letterSpacing = 0.sp,
    color = IosGray1
  )
)
