package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LiquidGlassColorScheme = darkColorScheme(
  primary = LiquidCyan,
  onPrimary = Color(0xFF031624),
  primaryContainer = Color(0xFF072C48),
  onPrimaryContainer = Color(0xFFBCE7FF),
  secondary = LiquidIceBlue,
  onSecondary = Color(0xFF002952),
  secondaryContainer = Color(0xFF133660),
  onSecondaryContainer = Color(0xFFD4E6FF),
  tertiary = LiquidViolet,
  onTertiary = Color(0xFF2C004F),
  background = LiquidSpaceDark,
  onBackground = TextPrimary,
  surface = LiquidSpaceSurface,
  onSurface = TextPrimary,
  surfaceVariant = Color(0xFF121B30),
  onSurfaceVariant = TextSecondary,
  outline = GlassBorderSubtle,
  outlineVariant = Color(0x334FACFE)
)

@Composable
fun Storage1xTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = LiquidGlassColorScheme,
    typography = Typography,
    content = content
  )
}
