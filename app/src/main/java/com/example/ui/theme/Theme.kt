package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MonochromeLiquidColorScheme = darkColorScheme(
  primary = PureWhite,
  onPrimary = PureBlack,
  primaryContainer = White18,
  onPrimaryContainer = PureWhite,
  secondary = White75,
  onSecondary = PureBlack,
  secondaryContainer = White12,
  onSecondaryContainer = PureWhite,
  tertiary = IosGray1,
  onTertiary = PureBlack,
  background = PureBlack,
  onBackground = PureWhite,
  surface = BlackDeep,
  onSurface = PureWhite,
  surfaceVariant = BlackSurface,
  onSurfaceVariant = White75,
  outline = White18,
  outlineVariant = White08
)

@Composable
fun Storage1xTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = MonochromeLiquidColorScheme,
    typography = Typography,
    content = content
  )
}
