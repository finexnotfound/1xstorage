package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Bold and Italic typography requested by user for 1x Storage minimalistic liquid glass look
val Typography = Typography(
  displayLarge = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Black,
    fontStyle = FontStyle.Italic,
    fontSize = 38.sp,
    lineHeight = 44.sp,
    letterSpacing = (-0.5).sp,
    color = TextPrimary
  ),
  displayMedium = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.ExtraBold,
    fontStyle = FontStyle.Italic,
    fontSize = 32.sp,
    lineHeight = 38.sp,
    letterSpacing = (-0.25).sp,
    color = TextPrimary
  ),
  headlineLarge = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 26.sp,
    lineHeight = 32.sp,
    color = TextPrimary
  ),
  headlineMedium = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    color = TextPrimary
  ),
  titleLarge = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 20.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.sp,
    color = TextPrimary
  ),
  titleMedium = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.15.sp,
    color = TextPrimary
  ),
  bodyLarge = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium,
    fontStyle = FontStyle.Italic,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.25.sp,
    color = TextPrimary
  ),
  bodyMedium = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Normal,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.25.sp,
    color = TextSecondary
  ),
  labelLarge = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.ExtraBold,
    fontStyle = FontStyle.Italic,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.5.sp,
    color = TextPrimary
  ),
  labelMedium = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = TextSecondary
  ),
  labelSmall = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    fontSize = 10.sp,
    lineHeight = 14.sp,
    letterSpacing = 0.5.sp,
    color = TextMuted
  )
)
