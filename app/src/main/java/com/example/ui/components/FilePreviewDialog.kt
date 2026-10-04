package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.FileCategory
import com.example.data.model.StorageFile
import com.example.ui.theme.GlassBorderCyan
import com.example.ui.theme.GlassBorderShine
import com.example.ui.theme.GlassBorderSubtle
import com.example.ui.theme.GlassFillDeep
import com.example.ui.theme.LiquidCyan
import com.example.ui.theme.LiquidIceBlue
import com.example.ui.theme.LiquidSpaceDark
import com.example.ui.theme.LiquidViolet
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.liquidGlass
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilePreviewDialog(
  file: StorageFile,
  onDismiss: () -> Unit,
  onShare: (StorageFile) -> Unit,
  onOpenBrowserCloudView: (StorageFile) -> Unit,
  onDelete: (StorageFile) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val hasLocalFile = file.localPath != null && File(file.localPath).exists()

  BasicAlertDialog(
    onDismissRequest = onDismiss,
    modifier = modifier.padding(16.dp)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .liquidGlass(
          shape = RoundedCornerShape(28.dp),
          backgroundColor = GlassFillDeep,
          borderColor = GlassBorderCyan
        )
        .padding(20.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Header with Close
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Color(0x2200F2FE)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Cloud,
                contentDescription = null,
                tint = LiquidCyan,
                modifier = Modifier.size(16.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "FILE PREVIEW",
              fontSize = 12.sp,
              fontWeight = FontWeight.Black,
              fontStyle = FontStyle.Italic,
              letterSpacing = 1.sp,
              color = LiquidCyan
            )
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(36.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close preview",
              tint = TextSecondary
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Visual Preview Container
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp, max = 240.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x250E172C))
            .border(1.dp, GlassBorderShine, RoundedCornerShape(20.dp)),
          contentAlignment = Alignment.Center
        ) {
          if (file.category == FileCategory.IMAGE && hasLocalFile) {
            AsyncImage(
              model = ImageRequest.Builder(context)
                .data(File(file.localPath!!))
                .crossfade(true)
                .build(),
              contentDescription = "Full Image Preview",
              contentScale = ContentScale.Fit,
              modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            )
          } else {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center,
              modifier = Modifier.padding(20.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(64.dp)
                  .clip(CircleShape)
                  .background(Color(0x1F00F2FE))
                  .border(1.dp, Color(0x4400F2FE), CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (file.category == FileCategory.IMAGE) Icons.Default.Image else Icons.Default.Description,
                  contentDescription = null,
                  tint = LiquidCyan,
                  modifier = Modifier.size(32.dp)
                )
              }
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = file.category.label.uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                letterSpacing = 1.sp,
                color = LiquidIceBlue
              )
              Text(
                text = file.mimeType,
                fontSize = 10.sp,
                color = TextMuted
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // File Title & Details
        Text(
          text = file.name,
          fontSize = 16.sp,
          fontWeight = FontWeight.Black,
          fontStyle = FontStyle.Italic,
          textAlign = TextAlign.Center,
          color = TextPrimary,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = file.formattedSize(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = LiquidCyan
          )
          Text(text = " • ", color = TextMuted)
          Text(
            text = file.formattedDate(),
            fontSize = 12.sp,
            color = TextSecondary
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Cloud Link Info Card
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0x1400F2FE))
            .border(1.dp, Color(0x3300F2FE), RoundedCornerShape(14.dp))
            .padding(10.dp)
        ) {
          Column {
            Text(
              text = "✦ 1X CLOUD SHARING LINK",
              fontSize = 10.sp,
              fontWeight = FontWeight.ExtraBold,
              fontStyle = FontStyle.Italic,
              color = LiquidCyan
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = file.cloudUrl,
              fontSize = 10.sp,
              color = TextSecondary,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))
        HorizontalDivider(color = Color(0x22FFFFFF))
        Spacer(modifier = Modifier.height(18.dp))

        // Action Buttons Row: Share & Cloud Browser View
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Share Button
          Box(
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(
                brush = Brush.horizontalGradient(
                  listOf(LiquidCyan, LiquidIceBlue)
                )
              )
              .clickable { onShare(file) }
              .testTag("dialog_share_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = LiquidSpaceDark,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "SHARE LINK",
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                color = LiquidSpaceDark
              )
            }
          }

          // Browser View on Cloud
          Box(
            modifier = Modifier
              .weight(1.2f)
              .height(48.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(Color(0x22FFFFFF))
              .border(1.2.dp, GlassBorderShine, RoundedCornerShape(14.dp))
              .clickable { onOpenBrowserCloudView(file) }
              .testTag("dialog_browser_view_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Language,
                contentDescription = null,
                tint = LiquidCyan,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "BROWSER VIEW",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                color = TextPrimary
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Open in App (if local) or Delete
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          if (hasLocalFile) {
            Box(
              modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0x18FFFFFF))
                .border(1.dp, GlassBorderSubtle, RoundedCornerShape(12.dp))
                .clickable {
                  try {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                      setDataAndType(Uri.fromFile(File(file.localPath!!)), file.mimeType)
                      addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(Intent.createChooser(intent, "Open with"))
                  } catch (_: Exception) {}
                },
              contentAlignment = Alignment.Center
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.OpenInNew,
                  contentDescription = null,
                  tint = TextSecondary,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Open on Device",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  fontStyle = FontStyle.Italic,
                  color = TextSecondary
                )
              }
            }
          }

          // Delete Button
          Box(
            modifier = Modifier
              .weight(1f)
              .height(40.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0x22FF5376))
              .border(1.dp, Color(0x44FF5376), RoundedCornerShape(12.dp))
              .clickable { onDelete(file) }
              .testTag("dialog_delete_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color(0xFFFF859E),
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Delete File",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(0xFFFF859E)
              )
            }
          }
        }
      }
    }
  }
}
