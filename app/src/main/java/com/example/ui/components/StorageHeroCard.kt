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
import com.example.ui.theme.GlassBorderSide
import com.example.ui.theme.GlassBorderTop
import com.example.ui.theme.IosGray1
import com.example.ui.theme.PureWhite
import com.example.ui.theme.White04
import com.example.ui.theme.White08
import com.example.ui.theme.White12
import com.example.ui.theme.White18
import com.example.ui.theme.White30
import com.example.ui.theme.White50
import com.example.ui.theme.White75
import com.example.ui.theme.liquidGlass

@Composable
fun StorageHeroCard(
  stats: StorageStats,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = stats.percentageUsed.coerceIn(0.01f, 1f),
    animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
    label = "storage_progress"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .liquidGlass(
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0x15FFFFFF),
        borderColor = GlassBorderTop
      )
      .padding(22.dp)
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
              .background(White12)
              .border(1.dp, White30, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.CloudDone,
              contentDescription = "Cloud Synced",
              tint = PureWhite,
              modifier = Modifier.size(18.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = "FREE CLOUD STORAGE",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontStyle = FontStyle.Italic,
              letterSpacing = 0.5.sp,
              color = PureWhite
            )
            Text(
              text = "Engineered by finex",
              fontSize = 11.sp,
              fontWeight = FontWeight.Normal,
              color = IosGray1
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(White12)
            .border(1.dp, White30, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
          Text(
            text = "50 GB FREE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = PureWhite
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Storage metrics (Black & White)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
      ) {
        Column {
          Text(
            text = stats.usedFormatted,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            letterSpacing = (-0.5).sp,
            color = PureWhite
          )
          Text(
            text = "used of ${stats.totalFormatted}",
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = White75
          )
        }

        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = stats.freeFormatted,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = PureWhite
          )
          Text(
            text = "available storage",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = IosGray1
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Monochromatic Liquid Capsule Progress Bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(10.dp)
          .clip(RoundedCornerShape(50.dp))
          .background(White08)
          .border(1.dp, White18, RoundedCornerShape(50.dp))
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth(animatedProgress)
            .fillMaxHeight()
            .clip(RoundedCornerShape(50.dp))
            .background(
              brush = Brush.horizontalGradient(
                colors = listOf(
                  PureWhite,
                  Color(0xDDFFFFFF)
                )
              )
            )
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Minimal Specs footer
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = null,
            tint = IosGray1,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "End-to-End Encrypted",
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = IosGray1
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Speed,
            contentDescription = null,
            tint = IosGray1,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "1x Cloud Uplink",
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = IosGray1
          )
        }
      }
    }
  }
}
