package com.tatsuki.appstoresdksample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazon.device.iap.model.Product
import com.amazon.device.iap.model.UserData
import com.tatsuki.appstoresdksample.amazon.AmazonPurchasingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
  private val amazonPurchasingService: AmazonPurchasingService,
) : ViewModel() {

  private val mutableUserDataFlow: MutableStateFlow<UserData?> = MutableStateFlow(null)
  val userDataFlow = mutableUserDataFlow.asStateFlow()

  private val mutableProductsFlow: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
  val productsFlow = mutableProductsFlow.asStateFlow()

  fun registerPurchasingService() {
    amazonPurchasingService.registerPurchasingService()
  }

  fun getUserData() {
    viewModelScope.launch {
      invoke {
        val userData = amazonPurchasingService.getUserData()
        mutableUserDataFlow.value = userData
      }
    }
  }

  fun getProductData() {
    viewModelScope.launch {
      invoke {
        val productSkus = HashSet<String>()
        productSkus.add(SUBSCRIPTION_ITEM_TEST_SKU)
        val products = amazonPurchasingService.getProductData(productSkus)
          .map { product -> product.value }
        mutableProductsFlow.value = products
      }
    }
  }

  private suspend fun invoke(block: suspend () -> Unit) {
    try {
      block()
    } catch (e: Exception) {
      coroutineContext.ensureActive()
      Log.e(TAG, e.stackTraceToString())
    }
  }

  companion object {
    private val TAG = MainViewModel::class.java.simpleName

    private val SUBSCRIPTION_ITEM_TEST_SKU = "subscription_item_test"
  }
}