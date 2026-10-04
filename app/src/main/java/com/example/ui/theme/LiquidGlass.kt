package com.example.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.liquidGlass(
  shape: Shape = RoundedCornerShape(22.dp),
  backgroundColor: Color = Color(0x12FFFFFF),
  borderColor: Color = Color(0x2EFFFFFF),
  borderWidth: Dp = 1.dp
): Modifier {
  return this
    .clip(shape)
    .background(
      brush = Brush.verticalGradient(
        colors = listOf(
          backgroundColor.copy(alpha = 0.16f),
          backgroundColor.copy(alpha = 0.05f)
        )
      ),
      shape = shape
    )
    .border(
      width = borderWidth,
      brush = Brush.linearGradient(
        colors = listOf(
          borderColor,
          Color(0x12FFFFFF),
          Color(0x06FFFFFF)
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
      ),
      shape = shape
    )
}

@Composable
fun Modifier.liquidGlassPill(
  shape: Shape = RoundedCornerShape(50.dp),
  isSelected: Boolean = false
): Modifier {
  val bgBrush = if (isSelected) {
    Brush.verticalGradient(listOf(Color(0xFFFFFFFF), Color(0xEEFFFFFF)))
  } else {
    Brush.verticalGradient(listOf(Color(0x18FFFFFF), Color(0x08FFFFFF)))
  }

  val borderBrush = if (isSelected) {
    Brush.linearGradient(listOf(Color(0xFFFFFFFF), Color(0xCCFFFFFF)))
  } else {
    Brush.linearGradient(listOf(Color(0x28FFFFFF), Color(0x0AFFFFFF)))
  }

  return this
    .clip(shape)
    .background(bgBrush, shape)
    .border(1.dp, borderBrush, shape)
}

@Composable
fun Modifier.liquidBackgroundEffect(): Modifier {
  val infiniteTransition = rememberInfiniteTransition(label = "liquid_monochrome")
  val phase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(24000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "liquid_phase"
  )

  return this.drawBehind {
    // Pure Black OLED Base
    drawRect(PureBlack)

    // Subtle Monochromatic Liquid Glass Refractions (Silvery specular sheen)
    val rad = Math.toRadians(phase.toDouble())
    val cx1 = (size.width * 0.3f) + (Math.cos(rad) * 50f).toFloat()
    val cy1 = (size.height * 0.2f) + (Math.sin(rad) * 60f).toFloat()

    val cx2 = (size.width * 0.8f) + (Math.sin(rad) * 50f).toFloat()
    val cy2 = (size.height * 0.6f) + (Math.cos(rad) * 60f).toFloat()

    // Smooth White Specular Drops (Ultra subtle 4-6% opacity for pristine luxury iOS feel)
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color(0x14FFFFFF), Color(0x00FFFFFF)),
        center = Offset(cx1, cy1),
        radius = size.width * 0.7f
      ),
      center = Offset(cx1, cy1),
      radius = size.width * 0.7f
    )

    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color(0x0EFFFFFF), Color(0x00FFFFFF)),
        center = Offset(cx2, cy2),
        radius = size.width * 0.65f
      ),
      center = Offset(cx2, cy2),
      radius = size.width * 0.65f
    )
  }
}
