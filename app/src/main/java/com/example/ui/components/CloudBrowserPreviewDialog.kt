package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.ui.theme.IosGray3
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
fun CloudBrowserPreviewDialog(
  file: StorageFile,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val hasLocalFile = file.localPath != null && File(file.localPath).exists()

  BasicAlertDialog(
    onDismissRequest = onDismiss,
    modifier = modifier.padding(14.dp)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .liquidGlass(
          shape = RoundedCornerShape(26.dp),
          backgroundColor = Color(0x18FFFFFF),
          borderColor = GlassBorderTop
        )
        .padding(18.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
      ) {
        // Browser Window Controls (Monochrome iOS style)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(9.dp)
                .clip(CircleShape)
                .background(White30)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(
              modifier = Modifier
                .size(9.dp)
                .clip(CircleShape)
                .background(White18)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(
              modifier = Modifier
                .size(9.dp)
                .clip(CircleShape)
                .background(White08)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
              text = "1x Cloud Web Viewer",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              fontStyle = FontStyle.Italic,
              color = White75
            )
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(28.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = IosGray1,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Web Address Bar (Monochrome)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x22000000))
            .border(1.dp, White18, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 7.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "Secure",
              tint = PureWhite,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = file.cloudUrl,
              fontSize = 10.sp,
              color = PureWhite,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.weight(1f)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Web Page Content Simulation
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0x12FFFFFF))
            .border(1.dp, White18, RoundedCornerShape(18.dp))
            .padding(18.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Web branding
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.Public,
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "1X STORAGE CLOUD",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                letterSpacing = 1.sp,
                color = PureWhite
              )
            }

            Text(
              text = "Shared via finex 50 GB Cloud Storage",
              fontSize = 11.sp,
              fontWeight = FontWeight.Normal,
              color = IosGray1,
              modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // File Web Preview Frame
            if (file.category == FileCategory.IMAGE && hasLocalFile) {
              Box(
                modifier = Modifier
                  .size(150.dp)
                  .clip(RoundedCornerShape(14.dp))
                  .background(Color(0x33000000))
                  .border(1.dp, White30, RoundedCornerShape(14.dp))
              ) {
                AsyncImage(
                  model = ImageRequest.Builder(context)
                    .data(File(file.localPath!!))
                    .crossfade(true)
                    .build(),
                  contentDescription = "Cloud Preview",
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.matchParentSize()
                )
              }
            } else {
              Box(
                modifier = Modifier
                  .size(70.dp)
                  .clip(CircleShape)
                  .background(White12)
                  .border(1.dp, White30, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.CloudDownload,
                  contentDescription = null,
                  tint = PureWhite,
                  modifier = Modifier.size(34.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
              text = file.name,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              fontStyle = FontStyle.Italic,
              color = PureWhite,
              textAlign = TextAlign.Center,
              maxLines = 2
            )

            Text(
              text = "${file.formattedSize()} • Ready for Cloud Download",
              fontSize = 12.sp,
              fontWeight = FontWeight.Normal,
              color = White75,
              modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Solid White Download Button
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PureWhite)
                .clickable {
                  Toast.makeText(context, "Cloud download initiated via 1x Storage", Toast.LENGTH_SHORT).show()
                },
              contentAlignment = Alignment.Center
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.CloudDownload,
                  contentDescription = null,
                  tint = PureBlack,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "DOWNLOAD FROM CLOUD",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  fontStyle = FontStyle.Italic,
                  color = PureBlack
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Actions: Copy Link & Open in System Browser (Monochrome)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Copy Link Button
          Box(
            modifier = Modifier
              .weight(1f)
              .height(42.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(White12)
              .border(1.dp, White30, RoundedCornerShape(12.dp))
              .clickable {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("1x Storage Cloud Link", file.cloudUrl)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, "Link copied to clipboard", Toast.LENGTH_SHORT).show()
              }
              .testTag("copy_cloud_link_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Copy Link",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = PureWhite
              )
            }
          }

          // Open in Browser Button
          Box(
            modifier = Modifier
              .weight(1f)
              .height(42.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(White08)
              .border(1.dp, White18, RoundedCornerShape(12.dp))
              .clickable {
                try {
                  val intent = Intent(Intent.ACTION_VIEW, Uri.parse(file.cloudUrl))
                  context.startActivity(intent)
                } catch (_: Exception) {
                  Toast.makeText(context, "Opening link: ${file.cloudUrl}", Toast.LENGTH_SHORT).show()
                }
              }
              .testTag("open_in_browser_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.OpenInBrowser,
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Open Browser",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = PureWhite
              )
            }
          }
        }
      }
    }
  }
}
