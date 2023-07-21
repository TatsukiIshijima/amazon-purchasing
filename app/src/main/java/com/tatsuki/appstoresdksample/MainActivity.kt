package com.tatsuki.appstoresdksample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.tatsuki.appstoresdksample.ui.compose.MainScreen
import com.tatsuki.appstoresdksample.ui.compose.ProductBody
import com.tatsuki.appstoresdksample.ui.compose.UserDataHeader
import com.tatsuki.appstoresdksample.ui.theme.AppStoreSDKSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val mainViewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mainViewModel.registerPurchasingService()

    with(mainViewModel) {
      productsFlow
        .onEach {
          Log.d(TAG, "Products=$it")
        }
        .launchIn(lifecycleScope)
    }

    setContent {
      AppStoreSDKSampleTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          MainScreen(
            header = {
              UserDataHeader(
                userId = mainViewModel.userDataFlow.collectAsState().value?.userId ?: "",
                marketplace = mainViewModel.userDataFlow.collectAsState().value?.marketplace ?: ""
              )
            },
            leftContent = {
              ProductBody(
                modifier = Modifier.fillMaxWidth(),
                productItems = mainViewModel.productsFlow.collectAsState().value
                  .map { ProductItem.from(it) },
                onClickItem = {
                  Log.d(TAG, "$it")
                }
              )
            },
            rightContent = {
//              ReceiptBody(
//                modifier = Modifier.fillMaxWidth(),
//                receiptItems = emptyList(),
//              )
            },
          )
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()

    with(mainViewModel) {
      getUserData()
      getProductData()
    }
  }

  companion object {
    private val TAG = MainActivity::class.java.simpleName
  }
}