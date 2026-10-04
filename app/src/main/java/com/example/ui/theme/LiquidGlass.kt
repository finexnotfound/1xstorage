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
  shape: Shape = RoundedCornerShape(24.dp),
  backgroundColor: Color = Color(0x1C182B4D),
  borderColor: Color = Color(0x38FFFFFF),
  borderWidth: Dp = 1.2.dp,
  glowColor: Color = Color(0x1500F2FE)
): Modifier {
  return this
    .clip(shape)
    .background(
      brush = Brush.verticalGradient(
        colors = listOf(
          backgroundColor.copy(alpha = 0.28f),
          backgroundColor.copy(alpha = 0.12f)
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
          glowColor,
          Color(0x05FFFFFF)
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
      ),
      shape = shape
    )
}

@Composable
fun Modifier.liquidGlassAccent(
  shape: Shape = RoundedCornerShape(20.dp),
  accentColor: Color = LiquidCyan
): Modifier {
  return this
    .clip(shape)
    .background(
      brush = Brush.linearGradient(
        colors = listOf(
          accentColor.copy(alpha = 0.22f),
          Color(0x1516243D)
        )
      ),
      shape = shape
    )
    .border(
      width = 1.dp,
      brush = Brush.linearGradient(
        colors = listOf(
          accentColor.copy(alpha = 0.6f),
          Color(0x18FFFFFF),
          accentColor.copy(alpha = 0.2f)
        )
      ),
      shape = shape
    )
}

@Composable
fun Modifier.liquidBackgroundEffect(): Modifier {
  val infiniteTransition = rememberInfiniteTransition(label = "liquid_aurora")
  val phase1 by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(20000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "phase1"
  )
  val phase2 by infiniteTransition.animateFloat(
    initialValue = 360f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(25000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "phase2"
  )

  return this.drawBehind {
    // Midnight background
    drawRect(LiquidSpaceDark)

    // Liquid glowing orbs with fluid drift
    val rad1 = Math.toRadians(phase1.toDouble())
    val rad2 = Math.toRadians(phase2.toDouble())

    val cx1 = (size.width * 0.25f) + (Math.cos(rad1) * 60f).toFloat()
    val cy1 = (size.height * 0.18f) + (Math.sin(rad1) * 80f).toFloat()

    val cx2 = (size.width * 0.85f) + (Math.sin(rad2) * 70f).toFloat()
    val cy2 = (size.height * 0.45f) + (Math.cos(rad2) * 60f).toFloat()

    val cx3 = (size.width * 0.45f) + (Math.sin(rad1 * 0.7) * 90f).toFloat()
    val cy3 = (size.height * 0.85f) + (Math.cos(rad2 * 0.7) * 70f).toFloat()

    // Cyan droplet orb
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color(0x2800F2FE), Color(0x0000F2FE)),
        center = Offset(cx1, cy1),
        radius = size.width * 0.75f
      ),
      center = Offset(cx1, cy1),
      radius = size.width * 0.75f
    )

    // Violet liquid glass orb
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color(0x24B154F0), Color(0x00B154F0)),
        center = Offset(cx2, cy2),
        radius = size.width * 0.7f
      ),
      center = Offset(cx2, cy2),
      radius = size.width * 0.7f
    )

    // Deep ice blue orb
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color(0x224FACFE), Color(0x004FACFE)),
        center = Offset(cx3, cy3),
        radius = size.width * 0.8f
      ),
      center = Offset(cx3, cy3),
      radius = size.width * 0.8f
    )
  }
}
