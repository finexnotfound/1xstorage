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
import com.example.ui.theme.GlassBorderTop
import com.example.ui.theme.IosGray1
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.White04
import com.example.ui.theme.White08
import com.example.ui.theme.White12
import com.example.ui.theme.White18
import com.example.ui.theme.White30
import com.example.ui.theme.White75
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
          backgroundColor = Color(0x18FFFFFF),
          borderColor = GlassBorderTop
        )
        .padding(20.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(White12),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Cloud,
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(15.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "PREVIEW",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              fontStyle = FontStyle.Italic,
              letterSpacing = 0.5.sp,
              color = PureWhite
            )
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = IosGray1
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Visual Preview Container
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp, max = 240.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0x22000000))
            .border(1.dp, White18, RoundedCornerShape(18.dp)),
          contentAlignment = Alignment.Center
        ) {
          if (file.category == FileCategory.IMAGE && hasLocalFile) {
            AsyncImage(
              model = ImageRequest.Builder(context)
                .data(File(file.localPath!!))
                .crossfade(true)
                .build(),
              contentDescription = "Image Preview",
              contentScale = ContentScale.Fit,
              modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
            )
          } else {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center,
              modifier = Modifier.padding(20.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(56.dp)
                  .clip(CircleShape)
                  .background(White12)
                  .border(1.dp, White30, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (file.category == FileCategory.IMAGE) Icons.Default.Image else Icons.Default.Description,
                  contentDescription = null,
                  tint = PureWhite,
                  modifier = Modifier.size(28.dp)
                )
              }
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = file.category.label.uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                letterSpacing = 0.5.sp,
                color = PureWhite
              )
              Text(
                text = file.mimeType,
                fontSize = 10.sp,
                color = IosGray1
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Title and size
        Text(
          text = file.name,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          fontStyle = FontStyle.Italic,
          textAlign = TextAlign.Center,
          color = PureWhite,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = file.formattedSize(),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = PureWhite
          )
          Text(text = " • ", color = IosGray1)
          Text(
            text = file.formattedDate(),
            fontSize = 12.sp,
            color = IosGray1
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Cloud Link Info Card (Monochrome)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(White08)
            .border(1.dp, White18, RoundedCornerShape(12.dp))
            .padding(10.dp)
        ) {
          Column {
            Text(
              text = "1X CLOUD SHARING LINK",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              fontStyle = FontStyle.Italic,
              letterSpacing = 0.5.sp,
              color = PureWhite
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = file.cloudUrl,
              fontSize = 10.sp,
              color = White75,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = White08)
        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons Row (Solid White & Frosted Glass)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Share Button (Solid White)
          Box(
            modifier = Modifier
              .weight(1f)
              .height(46.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(PureWhite)
              .clickable { onShare(file) }
              .testTag("dialog_share_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = PureBlack,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "SHARE LINK",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                color = PureBlack
              )
            }
          }

          // Browser View on Cloud (Frosted Glass)
          Box(
            modifier = Modifier
              .weight(1.2f)
              .height(46.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(White12)
              .border(1.dp, White30, RoundedCornerShape(14.dp))
              .clickable { onOpenBrowserCloudView(file) }
              .testTag("dialog_browser_view_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Language,
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "BROWSER VIEW",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                color = PureWhite
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Open in App or Delete (Monochrome)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          if (hasLocalFile) {
            Box(
              modifier = Modifier
                .weight(1f)
                .height(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(White08)
                .border(1.dp, White18, RoundedCornerShape(12.dp))
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
                  tint = PureWhite,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Open on Device",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  color = PureWhite
                )
              }
            }
          }

          // Delete Button (Monochrome subtle outline)
          Box(
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(White04)
              .border(1.dp, White18, RoundedCornerShape(12.dp))
              .clickable { onDelete(file) }
              .testTag("dialog_delete_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = IosGray1,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Delete File",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = IosGray1
              )
            }
          }
        }
      }
    }
  }
}
