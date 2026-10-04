package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StorageStats
import com.example.ui.theme.GlassBorderCyan
import com.example.ui.theme.GlassBorderShine
import com.example.ui.theme.GlassFillDeep
import com.example.ui.theme.LiquidCyan
import com.example.ui.theme.LiquidIceBlue
import com.example.ui.theme.LiquidTeal
import com.example.ui.theme.LiquidViolet
import com.example.ui.theme.TextCyanGlow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.liquidGlass

@Composable
fun StorageHeroCard(
  stats: StorageStats,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = stats.percentageUsed.coerceIn(0.01f, 1f),
    animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
    label = "storage_progress"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .liquidGlass(
        shape = RoundedCornerShape(26.dp),
        backgroundColor = GlassFillDeep,
        borderColor = GlassBorderCyan
      )
      .padding(20.dp)
  ) {
    Column {
      // Top Row: 1x Storage Badge & Free 50GB Pill
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(Color(0x2A00F2FE))
              .border(1.dp, Color(0x6600F2FE), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.CloudDone,
              contentDescription = "Cloud Synced",
              tint = LiquidCyan,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = "FREE CLOUD STORAGE",
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              fontStyle = FontStyle.Italic,
              letterSpacing = 1.sp,
              color = LiquidCyan
            )
            Text(
              text = "Powered by finex Cloud Engine",
              fontSize = 10.sp,
              fontWeight = FontWeight.Medium,
              fontStyle = FontStyle.Italic,
              color = TextSecondary
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
              brush = Brush.linearGradient(
                listOf(LiquidCyan.copy(alpha = 0.25f), LiquidViolet.copy(alpha = 0.25f))
              )
            )
            .border(1.dp, GlassBorderShine, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "50 GB FREE",
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            color = TextCyanGlow
          )
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Numbers & Status
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
      ) {
        Column {
          Text(
            text = stats.usedFormatted,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            fontStyle = FontStyle.Italic,
            color = TextPrimary
          )
          Text(
            text = "used of ${stats.totalFormatted}",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = TextSecondary
          )
        }

        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = stats.freeFormatted,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            color = LiquidTeal
          )
          Text(
            text = "remaining free space",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = TextMuted
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Liquid Glass Progress Bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(14.dp)
          .clip(RoundedCornerShape(50.dp))
          .background(Color(0x240A1326))
          .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(50.dp))
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth(animatedProgress)
            .fillMaxHeight()
            .clip(RoundedCornerShape(50.dp))
            .background(
              brush = Brush.horizontalGradient(
                colors = listOf(
                  LiquidCyan,
                  LiquidIceBlue,
                  LiquidViolet
                )
              )
            )
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Specs footer
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "End-to-End Cloud Encrypted",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = TextMuted
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Speed,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "1x High-Speed Uplink",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = TextMuted
          )
        }
      }
    }
  }
}
