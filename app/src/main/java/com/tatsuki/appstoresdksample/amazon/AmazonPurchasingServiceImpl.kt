package com.tatsuki.appstoresdksample.amazon

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.PurchasingService
import com.amazon.device.iap.model.FulfillmentResult
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
  private lateinit var requestPurchaseId: RequestId
  private lateinit var requestPurchaseUpdatesId: RequestId

  private val onUserDataListenerMap: MutableMap<RequestId, OnAmazonUserDataListener> =
    mutableMapOf()
  private val onProductDataListenerMap: MutableMap<RequestId, OnAmazonProductDataListener> =
    mutableMapOf()
  private val onPurchaseListenerMap: MutableMap<RequestId, OnAmazonPurchaseListener> =
    mutableMapOf()
  private val onPurchaseUpdatesListenerMap: MutableMap<RequestId, OnAmazonPurchaseUpdatesListener> =
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
              continuation.resumeWithException(AmazonPurchaseServiceException.GetUserDataFailedException)
            }

            UserDataResponse.RequestStatus.NOT_SUPPORTED -> {
              continuation.resumeWithException(AmazonPurchaseServiceException.NotSupportedException)
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
              continuation.resumeWithException(AmazonPurchaseServiceException.GetProductDataFailedException)
            }

            ProductDataResponse.RequestStatus.NOT_SUPPORTED -> {
              continuation.resumeWithException(AmazonPurchaseServiceException.NotSupportedException)
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

  private fun addOnAmazonPurchaseListener(
    requestId: RequestId,
    listener: OnAmazonPurchaseListener
  ) {
    onPurchaseListenerMap[requestId] = listener
  }

  private fun removeOnAmazonPurchaseListener(requestId: RequestId) {
    onPurchaseListenerMap.remove(requestId)
  }

  override suspend fun purchase(productSku: String): AmazonPurchasedReceipt {
    return suspendCancellableCoroutine { continuation ->
      requestPurchaseId = RequestId()
      val onAmazonPurchaseListener = object : OnAmazonPurchaseListener {
        override fun onPurchase(purchaseResponse: PurchaseResponse?) {
          removeOnAmazonPurchaseListener(requestPurchaseId)
          when (purchaseResponse?.requestStatus) {
            PurchaseResponse.RequestStatus.SUCCESSFUL -> {
              continuation.resume(
                AmazonPurchasedReceipt(
                  purchaseResponse.userData,
                  purchaseResponse.receipt
                )
              )
            }

            PurchaseResponse.RequestStatus.FAILED, null -> {
              continuation.resumeWithException(AmazonPurchaseServiceException.PurchaseFailedException)
            }

            PurchaseResponse.RequestStatus.INVALID_SKU -> {
              continuation.resumeWithException(AmazonPurchaseServiceException.InvalidSkuException)
            }

            PurchaseResponse.RequestStatus.ALREADY_PURCHASED -> {
              continuation.resumeWithException(AmazonPurchaseServiceException.AlreadyPurchasedException)
            }

            PurchaseResponse.RequestStatus.PENDING -> {
              continuation.resumeWithException(AmazonPurchaseServiceException.PendingException)
            }

            PurchaseResponse.RequestStatus.NOT_SUPPORTED -> {
              continuation.resumeWithException(AmazonPurchaseServiceException.NotSupportedException)
            }
          }
        }
      }
      continuation.invokeOnCancellation {
        removeOnAmazonPurchaseListener(requestPurchaseId)
      }
      addOnAmazonPurchaseListener(requestPurchaseId, onAmazonPurchaseListener)
      PurchasingService.purchase(productSku)
    }
  }

  override fun onPurchaseResponse(purchaseResponse: PurchaseResponse?) {
    onPurchaseListenerMap[requestPurchaseId]?.onPurchase(purchaseResponse)
  }

  override fun notifyFulfillment(receiptId: String, fulfillmentResult: FulfillmentResult) {
    PurchasingService.notifyFulfillment(receiptId, fulfillmentResult)
  }

  override suspend fun getPurchaseUpdates(requestAll: Boolean): List<AmazonPurchasedReceipt> {
    return suspendCancellableCoroutine { continuation ->
      requestPurchaseUpdatesId = RequestId()
      val amazonPurchasedReceipts = mutableListOf<AmazonPurchasedReceipt>()
      val onAmazonPurchaseUpdatesListener = object : OnAmazonPurchaseUpdatesListener {
        override fun onPurchaseUpdates(purchaseUpdatesResponse: PurchaseUpdatesResponse?) {
          when (purchaseUpdatesResponse?.requestStatus) {
            PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL -> {
              amazonPurchasedReceipts.addAll(
                purchaseUpdatesResponse.receipts.map {
                  AmazonPurchasedReceipt(
                    userData = purchaseUpdatesResponse.userData,
                    receipt = it
                  )
                }
              )
              if (purchaseUpdatesResponse.hasMore()) {
                PurchasingService.getPurchaseUpdates(false)
              } else {
                removeOnPurchaseUpdatesListener(requestPurchaseUpdatesId)
                continuation.resume(amazonPurchasedReceipts)
              }
            }

            PurchaseUpdatesResponse.RequestStatus.FAILED, null -> {
              removeOnPurchaseUpdatesListener(requestPurchaseUpdatesId)
              continuation.resumeWithException(AmazonPurchaseServiceException.PurchaseFailedException)
            }

            PurchaseUpdatesResponse.RequestStatus.NOT_SUPPORTED -> {
              removeOnPurchaseUpdatesListener(requestPurchaseUpdatesId)
              continuation.resumeWithException(AmazonPurchaseServiceException.NotSupportedException)
            }
          }
        }
      }
      continuation.invokeOnCancellation {
        removeOnPurchaseUpdatesListener(requestPurchaseUpdatesId)
      }
      addOnPurchaseUpdatesListener(requestPurchaseUpdatesId, onAmazonPurchaseUpdatesListener)
      PurchasingService.getPurchaseUpdates(requestAll)
    }
  }

  private fun addOnPurchaseUpdatesListener(
    requestId: RequestId,
    listener: OnAmazonPurchaseUpdatesListener
  ) {
    onPurchaseUpdatesListenerMap[requestId] = listener
  }

  private fun removeOnPurchaseUpdatesListener(requestId: RequestId) {
    onPurchaseUpdatesListenerMap.remove(requestId)
  }


  override fun onPurchaseUpdatesResponse(purchaseUpdatesResponse: PurchaseUpdatesResponse?) {
    onPurchaseUpdatesListenerMap[requestPurchaseUpdatesId]?.onPurchaseUpdates(
      purchaseUpdatesResponse
    )
  }
}