package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.ui.theme.GlassBorderTop
import com.example.ui.theme.IosGray1
import com.example.ui.theme.PureWhite
import com.example.ui.theme.White08
import com.example.ui.theme.White12
import com.example.ui.theme.White18
import com.example.ui.theme.White30
import com.example.ui.theme.White75
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
        backgroundColor = Color(0x12FFFFFF),
        borderColor = White18
      )
      .clickable { onClick() }
      .padding(14.dp)
      .testTag("file_item_${file.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Monochrome Thumbnail or Icon
      Box(
        modifier = Modifier
          .size(52.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(White08)
          .border(1.dp, White18, RoundedCornerShape(14.dp)),
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

          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PureWhite,
            modifier = Modifier.size(24.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(14.dp))

      // File Details
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = file.name,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          fontStyle = FontStyle.Italic,
          color = PureWhite,
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
            fontWeight = FontWeight.SemiBold,
            color = PureWhite
          )

          Text(
            text = " • ",
            fontSize = 12.sp,
            color = IosGray1
          )

          Text(
            text = file.formattedDate(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = IosGray1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.CloudDone,
            contentDescription = "Cloud Synced",
            tint = White75,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "1x Cloud Synced",
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = White75
          )
        }
      }

      Spacer(modifier = Modifier.width(6.dp))

      // Clean Share Button (Monochrome)
      IconButton(
        onClick = onShare,
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(White08)
          .border(1.dp, White18, CircleShape)
          .testTag("share_file_${file.id}")
      ) {
        Icon(
          imageVector = Icons.Default.Share,
          contentDescription = "Share File Link",
          tint = PureWhite,
          modifier = Modifier.size(17.dp)
        )
      }
    }
  }
}
