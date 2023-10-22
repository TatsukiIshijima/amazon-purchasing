package com.tatsuki.amazonpurchasingsample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.Product
import com.amazon.device.iap.model.UserData
import com.tatsuki.purchasing.AmazonPurchasingClient
import com.tatsuki.purchasing.feature.model.AmazonPurchasedReceipt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
  private val amazonPurchasingClient: AmazonPurchasingClient,
) : ViewModel() {

  private val mutableUserDataFlow: MutableStateFlow<UserData?> = MutableStateFlow(null)
  val userDataFlow = mutableUserDataFlow.asStateFlow()

  private val mutableProductsFlow: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
  val productsFlow = mutableProductsFlow.asStateFlow()

  private val mutablePurchasedReceiptsFlow: MutableStateFlow<List<AmazonPurchasedReceipt>> =
    MutableStateFlow(emptyList())
  val purchasedReceiptFlow = mutablePurchasedReceiptsFlow.asStateFlow()

  fun registerPurchasingService() {
    amazonPurchasingClient.registerPurchasingService()
  }

  fun getUserData() {
    viewModelScope.launch {
      invoke {
        val userData = amazonPurchasingClient.getUserData()
        mutableUserDataFlow.value = userData
      }
    }
  }

  fun getProductData() {
    viewModelScope.launch {
      invoke {
        val products = amazonPurchasingClient.getProductData(ProductSku.list)
          .map { product -> product.value }
        mutableProductsFlow.value = products
      }
    }
  }

  fun purchase(productSku: String) {
    viewModelScope.launch {
      invoke {
        val purchasedReceipt = amazonPurchasingClient.purchase(productSku)
        amazonPurchasingClient.notifyFulfillment(
          receiptId = purchasedReceipt.receipt.receiptId,
          fulfillmentResult = FulfillmentResult.FULFILLED,
        )
      }
    }
  }

  fun getPurchaseUpdates(requestAll: Boolean = false) {
    viewModelScope.launch {
      invoke {
        mutablePurchasedReceiptsFlow.value = amazonPurchasingClient.getPurchaseUpdates(requestAll)
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
  }
}
