package com.tatsuki.appstoresdksample.amazon

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.PurchasingService
import com.amazon.device.iap.model.Product
import com.amazon.device.iap.model.ProductDataResponse
import com.amazon.device.iap.model.PurchaseResponse
import com.amazon.device.iap.model.PurchaseUpdatesResponse
import com.amazon.device.iap.model.RequestId
import com.amazon.device.iap.model.UserData
import com.amazon.device.iap.model.UserDataResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AmazonPurchasingServiceImpl @Inject constructor(
  @ApplicationContext private val context: Context,
) : AmazonPurchasingService, PurchasingListener {

  private lateinit var requestUserDataId: RequestId
  private lateinit var requestProductDataId: RequestId

  private val onUserDataListenerMap: MutableMap<RequestId, OnAmazonUserDataListener> =
    mutableMapOf()
  private val onProductDataListenerMap: MutableMap<RequestId, OnAmazonProductDataListener> =
    mutableMapOf()

  override fun registerPurchasingService() {
    PurchasingService.registerListener(context, this)
  }

  private fun addOnAmazonUserDataListener(
    requestId: RequestId,
    listener: OnAmazonUserDataListener
  ) {
    onUserDataListenerMap[requestId] = listener
  }

  private fun removeOnAmazonUserDataListener(requestId: RequestId) {
    onUserDataListenerMap.remove(requestId)
  }

  override suspend fun getUserData(): UserData {
    return suspendCancellableCoroutine { continuation ->
      // com.amazon.device.iap.model.RequestIdはUUID.randomUUID()であるが、
      // PurchasingServiceのメソッドで返されるタイミングでは遅いので、先に作成する
      requestUserDataId = RequestId()
      val onAmazonUserDataListener = object : OnAmazonUserDataListener {
        override fun onUserData(userDataResponse: UserDataResponse?) {
          removeOnAmazonUserDataListener(requestUserDataId)
          when (userDataResponse?.requestStatus) {
            UserDataResponse.RequestStatus.SUCCESSFUL -> {
              continuation.resume(userDataResponse.userData)
            }

            UserDataResponse.RequestStatus.FAILED, null -> {
              continuation.resumeWithException(AmazonPurchaseException.GetUserDataFailedException)
            }

            UserDataResponse.RequestStatus.NOT_SUPPORTED -> {
              continuation.resumeWithException(AmazonPurchaseException.NotSupportedException)
            }
          }
        }
      }
      continuation.invokeOnCancellation {
        removeOnAmazonUserDataListener(requestUserDataId)
      }
      addOnAmazonUserDataListener(requestUserDataId, onAmazonUserDataListener)
      PurchasingService.getUserData()
    }
  }

  override fun onUserDataResponse(userDataResponse: UserDataResponse?) {
    onUserDataListenerMap[requestUserDataId]?.onUserData(userDataResponse)
  }

  private fun addOnAmazonProductDataListener(
    requestId: RequestId,
    listener: OnAmazonProductDataListener
  ) {
    onProductDataListenerMap[requestId] = listener
  }

  private fun removeOnAmazonProductDataListener(requestId: RequestId) {
    onProductDataListenerMap.remove(requestId)
  }

  override suspend fun getProductData(productSkus: Set<String>): Map<String, Product> {
    return suspendCancellableCoroutine { continuation ->
      requestProductDataId = RequestId()
      val onAmazonProductDataListener = object : OnAmazonProductDataListener {
        override fun onProductData(productDataResponse: ProductDataResponse?) {
          removeOnAmazonProductDataListener(requestProductDataId)
          when (productDataResponse?.requestStatus) {
            ProductDataResponse.RequestStatus.SUCCESSFUL -> {
              continuation.resume(productDataResponse.productData)
            }

            ProductDataResponse.RequestStatus.FAILED, null -> {
              continuation.resumeWithException(AmazonPurchaseException.GetProductDataFailedException)
            }

            ProductDataResponse.RequestStatus.NOT_SUPPORTED -> {
              continuation.resumeWithException(AmazonPurchaseException.NotSupportedException)
            }
          }
        }
      }
      continuation.invokeOnCancellation {
        removeOnAmazonProductDataListener(requestProductDataId)
      }
      addOnAmazonProductDataListener(requestProductDataId, onAmazonProductDataListener)
      PurchasingService.getProductData(productSkus)
    }
  }

  override fun onProductDataResponse(productDataResponse: ProductDataResponse?) {
    onProductDataListenerMap[requestProductDataId]?.onProductData(productDataResponse)
  }

  override fun onPurchaseResponse(p0: PurchaseResponse?) {
    TODO("Not yet implemented")
  }

  override fun onPurchaseUpdatesResponse(p0: PurchaseUpdatesResponse?) {
    TODO("Not yet implemented")
  }
}