package com.example.data.storage

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.data.model.FileCategory
import com.example.data.model.StorageFile
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class CloudStorageRepository(private val context: Context) {

  private val _files = MutableStateFlow<List<StorageFile>>(emptyList())
  val files: StateFlow<List<StorageFile>> = _files.asStateFlow()

  private val _isUploading = MutableStateFlow(false)
  val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

  private var firestore: FirebaseFirestore? = null
  private var currentUserId: String = "guest_default"
  private val prefs = context.getSharedPreferences("1x_storage_cache", Context.MODE_PRIVATE)

  init {
    try {
      if (FirebaseApp.getApps(context).isNotEmpty()) {
        firestore = FirebaseFirestore.getInstance()
      }
    } catch (e: Exception) {
      Log.w(TAG, "Firestore init deferred: ${e.message}")
    }
  }

  suspend fun loadFilesForUser(userId: String) = withContext(Dispatchers.IO) {
    currentUserId = userId
    // 1. Load cached files first
    val cached = loadCachedFiles(userId)
    if (cached.isNotEmpty()) {
      _files.value = cached
    } else {
      // Seed with initial starter items
      val seed = createInitialStarterFiles(userId)
      _files.value = seed
      saveCachedFiles(userId, seed)
    }

    // 2. Try Firestore sync
    try {
      val db = firestore
      if (db != null) {
        db.collection("users").document(userId).collection("files")
          .get()
          .addOnSuccessListener { snapshot ->
            if (snapshot != null && !snapshot.isEmpty) {
              val cloudList = snapshot.documents.mapNotNull { doc ->
                try {
                  StorageFile(
                    id = doc.getString("id") ?: doc.id,
                    userId = doc.getString("userId") ?: userId,
                    name = doc.getString("name") ?: "Unnamed",
                    sizeBytes = doc.getLong("sizeBytes") ?: 1024L,
                    mimeType = doc.getString("mimeType") ?: "application/octet-stream",
                    uploadTimestamp = doc.getLong("uploadTimestamp") ?: System.currentTimeMillis(),
                    localPath = doc.getString("localPath"),
                    cloudUrl = doc.getString("cloudUrl") ?: "",
                    category = FileCategory.valueOf(doc.getString("category") ?: "OTHER"),
                    isCloudSynced = true
                  )
                } catch (e: Exception) {
                  null
                }
              }
              if (cloudList.isNotEmpty()) {
                _files.value = cloudList
                saveCachedFiles(userId, cloudList)
              }
            }
          }
      }
    } catch (e: Exception) {
      Log.w(TAG, "Cloud sync fallback: ${e.message}")
    }
  }

  suspend fun uploadFromUri(uri: Uri): Result<StorageFile> = withContext(Dispatchers.IO) {
    _isUploading.value = true
    try {
      val resolver = context.contentResolver
      var fileName = "file_${System.currentTimeMillis()}"
      var fileSize: Long = 0L

      resolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
          val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
          val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
          if (nameIndex != -1) {
            val name = cursor.getString(nameIndex)
            if (!name.isNullOrBlank()) fileName = name
          }
          if (sizeIndex != -1) {
            fileSize = cursor.getLong(sizeIndex)
          }
        }
      }

      val mimeType = resolver.getType(uri) ?: "application/octet-stream"
      val fileId = UUID.randomUUID().toString()

      // Copy to internal storage uploads directory
      val uploadsDir = File(context.filesDir, "uploads").apply { mkdirs() }
      val destFile = File(uploadsDir, "${fileId}_${fileName.replace("[^a-zA-Z0-9._-]".toRegex(), "_")}")

      resolver.openInputStream(uri)?.use { input ->
        FileOutputStream(destFile).use { output ->
          input.copyTo(output)
        }
      }

      if (fileSize <= 0L && destFile.exists()) {
        fileSize = destFile.length()
      }

      val cloudShareUrl = StorageFile.buildCloudShareUrl(fileId, fileName, fileSize)
      val newFile = StorageFile(
        id = fileId,
        userId = currentUserId,
        name = fileName,
        sizeBytes = fileSize,
        mimeType = mimeType,
        uploadTimestamp = System.currentTimeMillis(),
        localPath = destFile.absolutePath,
        cloudUrl = cloudShareUrl,
        category = StorageFile.determineCategory(fileName, mimeType),
        isCloudSynced = true
      )

      val updatedList = listOf(newFile) + _files.value
      _files.value = updatedList
      saveCachedFiles(currentUserId, updatedList)

      // Sync metadata to Firestore
      try {
        val db = firestore
        if (db != null) {
          val map = hashMapOf(
            "id" to newFile.id,
            "userId" to newFile.userId,
            "name" to newFile.name,
            "sizeBytes" to newFile.sizeBytes,
            "mimeType" to newFile.mimeType,
            "uploadTimestamp" to newFile.uploadTimestamp,
            "localPath" to newFile.localPath,
            "cloudUrl" to newFile.cloudUrl,
            "category" to newFile.category.name,
            "isCloudSynced" to true
          )
          db.collection("users").document(currentUserId).collection("files")
            .document(newFile.id)
            .set(map)
        }
      } catch (e: Exception) {
        Log.w(TAG, "Firestore write warning: ${e.message}")
      }

      Result.success(newFile)
    } catch (e: Exception) {
      Log.e(TAG, "Upload failed", e)
      Result.failure(e)
    } finally {
      _isUploading.value = false
    }
  }

  suspend fun deleteFile(file: StorageFile) = withContext(Dispatchers.IO) {
    try {
      file.localPath?.let { path ->
        val f = File(path)
        if (f.exists()) f.delete()
      }
      val updatedList = _files.value.filter { it.id != file.id }
      _files.value = updatedList
      saveCachedFiles(currentUserId, updatedList)

      firestore?.collection("users")?.document(currentUserId)
        ?.collection("files")?.document(file.id)?.delete()
    } catch (e: Exception) {
      Log.e(TAG, "Delete error", e)
    }
  }

  private fun createInitialStarterFiles(userId: String): List<StorageFile> {
    val welcomeId = "intro_docx_01"
    val heroId = "liquid_hero_02"
    val finexSpecId = "spec_pdf_03"

    val heroPath = File(context.filesDir, "liquid_glass_hero.jpg").apply {
      if (!exists()) {
        try {
          val resFile = File(context.applicationInfo.dataDir, "res/drawable/liquid_glass_hero.jpg")
          if (resFile.exists()) resFile.copyTo(this, true)
        } catch (_: Exception) {}
      }
    }.absolutePath

    return listOf(
      StorageFile(
        id = welcomeId,
        userId = userId,
        name = "1x Storage - Free 50GB Welcome Guide.pdf",
        sizeBytes = 1420000L,
        mimeType = "application/pdf",
        uploadTimestamp = System.currentTimeMillis() - 3600000L * 24,
        localPath = null,
        cloudUrl = StorageFile.buildCloudShareUrl(welcomeId, "1x Storage - Free 50GB Welcome Guide.pdf", 1420000L),
        category = FileCategory.DOCUMENT,
        isCloudSynced = true
      ),
      StorageFile(
        id = heroId,
        userId = userId,
        name = "liquid_glass_aurora_finex.jpg",
        sizeBytes = 3840000L,
        mimeType = "image/jpeg",
        uploadTimestamp = System.currentTimeMillis() - 3600000L * 6,
        localPath = heroPath,
        cloudUrl = StorageFile.buildCloudShareUrl(heroId, "liquid_glass_aurora_finex.jpg", 3840000L),
        category = FileCategory.IMAGE,
        isCloudSynced = true
      ),
      StorageFile(
        id = finexSpecId,
        userId = userId,
        name = "finex Cloud Protocol v2.5.zip",
        sizeBytes = 28600000L,
        mimeType = "application/zip",
        uploadTimestamp = System.currentTimeMillis() - 3600000L * 2,
        localPath = null,
        cloudUrl = StorageFile.buildCloudShareUrl(finexSpecId, "finex Cloud Protocol v2.5.zip", 28600000L),
        category = FileCategory.ARCHIVE,
        isCloudSynced = true
      )
    )
  }

  private fun saveCachedFiles(userId: String, files: List<StorageFile>) {
    try {
      val array = JSONArray()
      for (file in files) {
        val obj = JSONObject().apply {
          put("id", file.id)
          put("userId", file.userId)
          put("name", file.name)
          put("sizeBytes", file.sizeBytes)
          put("mimeType", file.mimeType)
          put("uploadTimestamp", file.uploadTimestamp)
          put("localPath", file.localPath ?: "")
          put("cloudUrl", file.cloudUrl)
          put("category", file.category.name)
          put("isCloudSynced", file.isCloudSynced)
        }
        array.put(obj)
      }
      prefs.edit().putString("files_$userId", array.toString()).apply()
    } catch (e: Exception) {
      Log.e(TAG, "Cache save error", e)
    }
  }

  private fun loadCachedFiles(userId: String): List<StorageFile> {
    val json = prefs.getString("files_$userId", null) ?: return emptyList()
    return try {
      val array = JSONArray(json)
      val list = mutableListOf<StorageFile>()
      for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        list.add(
          StorageFile(
            id = obj.getString("id"),
            userId = obj.optString("userId", userId),
            name = obj.getString("name"),
            sizeBytes = obj.getLong("sizeBytes"),
            mimeType = obj.getString("mimeType"),
            uploadTimestamp = obj.getLong("uploadTimestamp"),
            localPath = obj.optString("localPath").ifBlank { null },
            cloudUrl = obj.getString("cloudUrl"),
            category = FileCategory.valueOf(obj.optString("category", "OTHER")),
            isCloudSynced = obj.optBoolean("isCloudSynced", true)
          )
        )
      }
      list
    } catch (e: Exception) {
      emptyList()
    }
  }

  companion object {
    private const val TAG = "CloudStorageRepo"
  }
}
