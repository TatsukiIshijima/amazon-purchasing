package com.tatsuki.appstoresdksample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.tatsuki.appstoresdksample.ui.theme.AppStoreSDKSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val mainViewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mainViewModel.registerPurchasingService()

    with(mainViewModel) {
      userDataFlow
        .filterNotNull()
        .onEach {
          Log.d(TAG, "UserData=$it")
        }.launchIn(lifecycleScope)
    }

    setContent {
      AppStoreSDKSampleTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Greeting()
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()

    mainViewModel.getUserData()
  }

  companion object {
    private val TAG = MainActivity::class.java.simpleName
  }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
  Text(
    text = stringResource(id = R.string.app_name),
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  AppStoreSDKSampleTheme {
    Greeting()
  }
}