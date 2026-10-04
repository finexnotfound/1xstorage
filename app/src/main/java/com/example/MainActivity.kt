package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.auth.AuthState
import com.example.ui.MainViewModel
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.LiquidSpaceDark
import com.example.ui.theme.Storage1xTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Storage1xTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = LiquidSpaceDark
        ) {
          Storage1xApp()
        }
      }
    }
  }
}

@Composable
fun Storage1xApp(
  viewModel: MainViewModel = viewModel()
) {
  val authState by viewModel.authState.collectAsState()
  val previewFile by viewModel.previewFile.collectAsState()
  val browserCloudFile by viewModel.browserCloudFile.collectAsState()

  // Back handling for dialogs
  BackHandler(enabled = previewFile != null || browserCloudFile != null) {
    if (browserCloudFile != null) {
      viewModel.closeBrowserCloudView()
    } else if (previewFile != null) {
      viewModel.closePreview()
    }
  }

  when (val state = authState) {
    is AuthState.Authenticated -> {
      HomeScreen(
        user = state.user,
        viewModel = viewModel
      )
    }
    else -> {
      AuthScreen(
        viewModel = viewModel,
        authState = state
      )
    }
  }
}
