package com.example.ui

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthManager
import com.example.data.auth.AuthState
import com.example.data.auth.AuthUser
import com.example.data.model.FileCategory
import com.example.data.model.StorageFile
import com.example.data.storage.CloudStorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

data class StorageStats(
  val totalQuotaBytes: Long = StorageFile.MAX_STORAGE_BYTES,
  val usedBytes: Long = 0L,
  val freeBytes: Long = StorageFile.MAX_STORAGE_BYTES,
  val usedFormatted: String = "0 B",
  val freeFormatted: String = "50.0 GB",
  val totalFormatted: String = "50.0 GB",
  val percentageUsed: Float = 0f
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

  val authManager = AuthManager(application)
  private val repository = CloudStorageRepository(application)

  val authState: StateFlow<AuthState> = authManager.authState
  val isUploading: StateFlow<Boolean> = repository.isUploading

  private val _selectedCategory = MutableStateFlow(FileCategory.ALL)
  val selectedCategory: StateFlow<FileCategory> = _selectedCategory.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _previewFile = MutableStateFlow<StorageFile?>(null)
  val previewFile: StateFlow<StorageFile?> = _previewFile.asStateFlow()

  private val _browserCloudFile = MutableStateFlow<StorageFile?>(null)
  val browserCloudFile: StateFlow<StorageFile?> = _browserCloudFile.asStateFlow()

  private val _toastMessage = MutableStateFlow<String?>(null)
  val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

  val allFiles: StateFlow<List<StorageFile>> = repository.files

  // Filtered files based on category and search query
  val filteredFiles: StateFlow<List<StorageFile>> = combine(
    repository.files,
    _selectedCategory,
    _searchQuery
  ) { files, category, query ->
    files.filter { file ->
      val matchesCategory = (category == FileCategory.ALL) || (file.category == category)
      val matchesQuery = query.isBlank() || file.name.contains(query, ignoreCase = true)
      matchesCategory && matchesQuery
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Storage usage metrics out of 50.0 GB
  val storageStats: StateFlow<StorageStats> = repository.files.combine(_selectedCategory) { files, _ ->
    val used = files.sumOf { it.sizeBytes }
    val total = StorageFile.MAX_STORAGE_BYTES
    val free = (total - used).coerceAtLeast(0L)
    val pct = (used.toDouble() / total.toDouble()).toFloat().coerceIn(0.001f, 1f)

    StorageStats(
      totalQuotaBytes = total,
      usedBytes = used,
      freeBytes = free,
      usedFormatted = formatBytes(used),
      freeFormatted = formatBytes(free),
      totalFormatted = "50.0 GB",
      percentageUsed = pct
    )
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StorageStats())

  init {
    viewModelScope.launch {
      authState.collect { state ->
        if (state is AuthState.Authenticated) {
          repository.loadFilesForUser(state.user.uid)
        }
      }
    }
  }

  fun setCategory(category: FileCategory) {
    _selectedCategory.value = category
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun openPreview(file: StorageFile) {
    _previewFile.value = file
  }

  fun closePreview() {
    _previewFile.value = null
  }

  fun openBrowserCloudView(file: StorageFile) {
    _browserCloudFile.value = file
  }

  fun closeBrowserCloudView() {
    _browserCloudFile.value = null
  }

  fun clearToast() {
    _toastMessage.value = null
  }

  fun uploadFile(uri: Uri) {
    viewModelScope.launch {
      val result = repository.uploadFromUri(uri)
      if (result.isSuccess) {
        _toastMessage.value = "Uploaded: ${result.getOrNull()?.name}"
      } else {
        _toastMessage.value = "Upload failed: ${result.exceptionOrNull()?.localizedMessage}"
      }
    }
  }

  fun deleteFile(file: StorageFile) {
    viewModelScope.launch {
      repository.deleteFile(file)
      if (_previewFile.value?.id == file.id) {
        _previewFile.value = null
      }
      _toastMessage.value = "Deleted ${file.name}"
    }
  }

  fun signInWithEmail(email: String, pass: String) {
    viewModelScope.launch {
      authManager.signInWithEmail(email, pass)
    }
  }

  fun signUpWithEmail(email: String, pass: String, name: String) {
    viewModelScope.launch {
      authManager.signUpWithEmail(email, pass, name)
    }
  }

  fun signInWithGoogle(context: Context) {
    viewModelScope.launch {
      authManager.signInWithGoogle(context)
    }
  }

  fun continueAsDemo() {
    authManager.continueAsDemo()
  }

  fun signOut() {
    authManager.signOut()
    _previewFile.value = null
    _browserCloudFile.value = null
  }

  private fun formatBytes(bytes: Long): String {
    if (bytes <= 0) return "0 B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(bytes.toDouble()) / Math.log10(1024.0)).toInt()
    val value = bytes / Math.pow(1024.0, digitGroups.toDouble())
    return String.format(Locale.US, "%.1f %s", value, units[digitGroups.coerceIn(0, units.size - 1)])
  }
}
