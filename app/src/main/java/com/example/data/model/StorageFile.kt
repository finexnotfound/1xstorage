package com.example.data.model

import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class FileCategory(val label: String) {
  ALL("All"),
  IMAGE("Images"),
  DOCUMENT("Documents"),
  MEDIA("Media"),
  ARCHIVE("Archives"),
  OTHER("Other")
}

data class StorageFile(
  val id: String = "",
  val userId: String = "",
  val name: String = "",
  val sizeBytes: Long = 0L,
  val mimeType: String = "",
  val uploadTimestamp: Long = System.currentTimeMillis(),
  val localPath: String? = null,
  val cloudUrl: String = "",
  val category: FileCategory = FileCategory.OTHER,
  val isCloudSynced: Boolean = true
) {
  fun formattedSize(): String {
    if (sizeBytes <= 0) return "0 B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(sizeBytes.toDouble()) / Math.log10(1024.0)).toInt()
    val value = sizeBytes / Math.pow(1024.0, digitGroups.toDouble())
    return String.format(Locale.US, "%.1f %s", value, units[digitGroups.coerceIn(0, units.size - 1)])
  }

  fun formattedDate(): String {
    val sdf = SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault())
    return sdf.format(Date(uploadTimestamp))
  }

  companion object {
    const val MAX_STORAGE_BYTES: Long = 50L * 1024L * 1024L * 1024L // 50 GB FREE CLOUD STORAGE

    fun determineCategory(fileName: String, mimeType: String): FileCategory {
      val ext = fileName.substringAfterLast('.', "").lowercase(Locale.ROOT)
      return when {
        mimeType.startsWith("image/") || ext in listOf("png", "jpg", "jpeg", "webp", "gif", "svg", "bmp") ->
          FileCategory.IMAGE
        mimeType.startsWith("video/") || mimeType.startsWith("audio/") || ext in listOf("mp4", "mkv", "mov", "mp3", "wav", "m4a", "flac") ->
          FileCategory.MEDIA
        mimeType.contains("pdf") || mimeType.contains("word") || mimeType.contains("text") || ext in listOf("pdf", "doc", "docx", "txt", "md", "xls", "xlsx", "ppt", "pptx", "csv") ->
          FileCategory.DOCUMENT
        ext in listOf("zip", "rar", "7z", "tar", "gz") ->
          FileCategory.ARCHIVE
        else -> FileCategory.OTHER
      }
    }

    fun buildCloudShareUrl(fileId: String, fileName: String, sizeBytes: Long): String {
      val encodedName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
      return "https://ais-pre-xeisupu34p2g7ycttcfw26-552144887852.asia-southeast1.run.app/share?id=$fileId&title=$encodedName&size=$sizeBytes&engine=finex"
    }
  }
}
