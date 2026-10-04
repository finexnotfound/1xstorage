package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.auth.AuthUser
import com.example.data.model.FileCategory
import com.example.data.model.StorageFile
import com.example.ui.MainViewModel
import com.example.ui.components.CloudBrowserPreviewDialog
import com.example.ui.components.FileItemCard
import com.example.ui.components.FilePreviewDialog
import com.example.ui.components.StorageHeroCard
import com.example.ui.theme.IosGray1
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.White04
import com.example.ui.theme.White08
import com.example.ui.theme.White12
import com.example.ui.theme.White18
import com.example.ui.theme.White30
import com.example.ui.theme.White75
import com.example.ui.theme.liquidBackgroundEffect
import com.example.ui.theme.liquidGlass

@Composable
fun HomeScreen(
  user: AuthUser,
  viewModel: MainViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  val files by viewModel.filteredFiles.collectAsState()
  val allFiles by viewModel.allFiles.collectAsState()
  val stats by viewModel.storageStats.collectAsState()
  val selectedCategory by viewModel.selectedCategory.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val isUploading by viewModel.isUploading.collectAsState()
  val previewFile by viewModel.previewFile.collectAsState()
  val browserCloudFile by viewModel.browserCloudFile.collectAsState()
  val toastMessage by viewModel.toastMessage.collectAsState()

  LaunchedEffect(toastMessage) {
    toastMessage?.let {
      Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
      viewModel.clearToast()
    }
  }

  // Document/File picker
  val filePickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri ->
    uri?.let { viewModel.uploadFile(it) }
  }

  // Photo/Media Picker
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri ->
    uri?.let { viewModel.uploadFile(it) }
  }

  // Share helper
  val shareFile: (StorageFile) -> Unit = { file ->
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_SUBJECT, "1x Storage File: ${file.name}")
      putExtra(
        Intent.EXTRA_TEXT,
        "Here is '${file.name}' (${file.formattedSize()}) shared via 1x Storage by finex (Free 50 GB Cloud Storage):\n\n${file.cloudUrl}\n\nClick to view and download on the cloud browser!"
      )
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share file on 1x Cloud via"))
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .liquidBackgroundEffect()
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    Column(
      modifier = Modifier.fillMaxSize()
    ) {
      // Top Header (Monochrome iOS)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(White12)
                .border(1.dp, White30, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Cloud,
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(16.dp)
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
              text = "1X STORAGE",
              fontSize = 19.sp,
              fontWeight = FontWeight.Bold,
              fontStyle = FontStyle.Italic,
              letterSpacing = 0.5.sp,
              color = PureWhite
            )
          }

          Text(
            text = "MADE BY FINEX",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.5.sp,
            color = IosGray1,
            modifier = Modifier.padding(start = 36.dp)
          )
        }

        // User Pill and Logout
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(White08)
            .border(1.dp, White18, RoundedCornerShape(20.dp))
            .padding(start = 10.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
        ) {
          Text(
            text = user.displayName.take(14),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Italic,
            color = White75
          )

          Spacer(modifier = Modifier.width(4.dp))

          IconButton(
            onClick = { viewModel.signOut() },
            modifier = Modifier.size(28.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Logout,
              contentDescription = "Sign out",
              tint = IosGray1,
              modifier = Modifier.size(15.dp)
            )
          }
        }
      }

      // Scrollable Content
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp)
      ) {
        // Hero Card showing 50 GB free storage (Monochrome)
        item {
          StorageHeroCard(stats = stats)
          Spacer(modifier = Modifier.height(18.dp))
        }

        // Upload Buttons Action Row
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // Main Upload Button (Solid Pure White)
            Box(
              modifier = Modifier
                .weight(1.3f)
                .height(50.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(PureWhite)
                .clickable(enabled = !isUploading) {
                  filePickerLauncher.launch("*/*")
                }
                .testTag("upload_file_button"),
              contentAlignment = Alignment.Center
            ) {
              if (isUploading) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  CircularProgressIndicator(
                    color = PureBlack,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "UPLOADING...",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    color = PureBlack
                  )
                }
              } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.CloudUpload,
                    contentDescription = null,
                    tint = PureBlack,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "UPLOAD FILE",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 13.sp,
                    color = PureBlack
                  )
                }
              }
            }

            // Media Upload Button (Frosted Glass)
            Box(
              modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(White12)
                .border(1.dp, White30, RoundedCornerShape(16.dp))
                .clickable(enabled = !isUploading) {
                  photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                  )
                }
                .testTag("upload_photo_button"),
              contentAlignment = Alignment.Center
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.AddPhotoAlternate,
                  contentDescription = null,
                  tint = PureWhite,
                  modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "MEDIA",
                  fontWeight = FontWeight.Bold,
                  fontStyle = FontStyle.Italic,
                  fontSize = 13.sp,
                  color = PureWhite
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(18.dp))
        }

        // Search Bar (Monochrome iOS)
        item {
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            placeholder = {
              Text(
                text = "Search my files...",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Normal,
                color = IosGray1
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = PureWhite
              )
            },
            trailingIcon = {
              if (searchQuery.isNotBlank()) {
                IconButton(onClick = { viewModel.setSearchQuery("") }) {
                  Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear",
                    tint = IosGray1
                  )
                }
              }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = PureWhite,
              unfocusedBorderColor = White18,
              focusedContainerColor = White08,
              unfocusedContainerColor = White04,
              focusedTextColor = PureWhite,
              unfocusedTextColor = PureWhite
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("search_files_input")
          )

          Spacer(modifier = Modifier.height(14.dp))
        }

        // Category Filter Chips (Black and White Only)
        item {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            FileCategory.values().forEach { category ->
              val isSelected = category == selectedCategory
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(if (isSelected) PureWhite else White08)
                  .border(
                    width = 1.dp,
                    color = if (isSelected) PureWhite else White18,
                    shape = RoundedCornerShape(12.dp)
                  )
                  .clickable { viewModel.setCategory(category) }
                  .padding(horizontal = 14.dp, vertical = 7.dp)
                  .testTag("filter_chip_${category.name}"),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = category.label,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  fontStyle = FontStyle.Italic,
                  fontSize = 12.sp,
                  color = if (isSelected) PureBlack else White75
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(18.dp))
        }

        // Section Title: MY FILES
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "MY FILES",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                letterSpacing = 0.5.sp,
                color = PureWhite
              )

              Spacer(modifier = Modifier.width(8.dp))

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(White12)
                  .padding(horizontal = 7.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "${files.size}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  fontStyle = FontStyle.Italic,
                  color = PureWhite
                )
              }
            }

            Text(
              text = "Tap to preview • Share",
              fontSize = 11.sp,
              fontWeight = FontWeight.Normal,
              color = IosGray1
            )
          }

          Spacer(modifier = Modifier.height(12.dp))
        }

        // Empty State or List
        if (files.isEmpty()) {
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .liquidGlass(
                  shape = RoundedCornerShape(20.dp),
                  backgroundColor = Color(0x10FFFFFF),
                  borderColor = White18
                )
                .padding(30.dp),
              contentAlignment = Alignment.Center
            ) {
              Column(
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Icon(
                  imageVector = Icons.Default.CloudQueue,
                  contentDescription = null,
                  tint = IosGray1,
                  modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                  text = if (searchQuery.isNotBlank()) "No files match \"$searchQuery\"" else "No files in ${selectedCategory.label}",
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  fontStyle = FontStyle.Italic,
                  color = PureWhite,
                  textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = "Upload any document, image, or media file to store in your free 50 GB cloud.",
                  fontSize = 12.sp,
                  color = White75,
                  textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(White12)
                    .border(1.dp, White30, RoundedCornerShape(12.dp))
                    .clickable { filePickerLauncher.launch("*/*") }
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                ) {
                  Text(
                    text = "+ Upload New File",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = PureWhite
                  )
                }
              }
            }
          }
        } else {
          items(files, key = { it.id }) { file ->
            FileItemCard(
              file = file,
              onClick = { viewModel.openPreview(file) },
              onShare = { shareFile(file) }
            )
            Spacer(modifier = Modifier.height(10.dp))
          }
        }
      }
    }

    // Modal File Preview Dialog (Monochrome)
    previewFile?.let { file ->
      FilePreviewDialog(
        file = file,
        onDismiss = { viewModel.closePreview() },
        onShare = { shareFile(it) },
        onOpenBrowserCloudView = {
          viewModel.closePreview()
          viewModel.openBrowserCloudView(it)
        },
        onDelete = { viewModel.deleteFile(it) }
      )
    }

    // Modal Cloud Web Browser Preview Dialog (Monochrome)
    browserCloudFile?.let { file ->
      CloudBrowserPreviewDialog(
        file = file,
        onDismiss = { viewModel.closeBrowserCloudView() }
      )
    }
  }
}
