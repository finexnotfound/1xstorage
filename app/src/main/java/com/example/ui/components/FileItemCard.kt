package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.FileCategory
import com.example.data.model.StorageFile
import com.example.ui.theme.GlassBorderShine
import com.example.ui.theme.GlassBorderSubtle
import com.example.ui.theme.GlassFillDeep
import com.example.ui.theme.LiquidCyan
import com.example.ui.theme.LiquidTeal
import com.example.ui.theme.LiquidViolet
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.liquidGlass
import java.io.File

@Composable
fun FileItemCard(
  file: StorageFile,
  onClick: () -> Unit,
  onShare: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Box(
    modifier = modifier
      .fillMaxWidth()
      .liquidGlass(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = GlassFillDeep,
        borderColor = GlassBorderSubtle
      )
      .clickable { onClick() }
      .padding(14.dp)
      .testTag("file_item_${file.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Thumbnail or Category Icon
      Box(
        modifier = Modifier
          .size(54.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(Color(0x2213233F))
          .border(1.dp, GlassBorderShine, RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
      ) {
        val hasLocalFile = file.localPath != null && File(file.localPath).exists()
        if (file.category == FileCategory.IMAGE && hasLocalFile) {
          AsyncImage(
            model = ImageRequest.Builder(context)
              .data(File(file.localPath!!))
              .crossfade(true)
              .build(),
            contentDescription = "Image preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
        } else {
          val icon = when (file.category) {
            FileCategory.IMAGE -> Icons.Default.Image
            FileCategory.DOCUMENT -> Icons.Default.Description
            FileCategory.MEDIA -> Icons.Default.PlayCircle
            FileCategory.ARCHIVE -> Icons.Default.Archive
            else -> Icons.Default.InsertDriveFile
          }
          val iconTint = when (file.category) {
            FileCategory.IMAGE -> LiquidCyan
            FileCategory.DOCUMENT -> LiquidIceBlueColor
            FileCategory.MEDIA -> LiquidViolet
            FileCategory.ARCHIVE -> LiquidTeal
            else -> TextSecondary
          }

          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(28.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(14.dp))

      // File Info Column
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = file.name,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          fontStyle = FontStyle.Italic,
          color = TextPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(3.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = file.formattedSize(),
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            color = LiquidCyan
          )

          Text(
            text = " • ",
            fontSize = 12.sp,
            color = TextMuted
          )

          Text(
            text = file.formattedDate(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = TextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.CloudDone,
            contentDescription = "Cloud synced",
            tint = LiquidTeal,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Synced on 1x Cloud",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = LiquidTeal
          )
        }
      }

      Spacer(modifier = Modifier.width(6.dp))

      // Direct Share Button
      IconButton(
        onClick = onShare,
        modifier = Modifier
          .size(42.dp)
          .clip(CircleShape)
          .background(Color(0x1800F2FE))
          .border(1.dp, Color(0x3300F2FE), CircleShape)
          .testTag("share_file_${file.id}")
      ) {
        Icon(
          imageVector = Icons.Default.Share,
          contentDescription = "Share File Link",
          tint = LiquidCyan,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

private val LiquidIceBlueColor = Color(0xFF4FACFE)
