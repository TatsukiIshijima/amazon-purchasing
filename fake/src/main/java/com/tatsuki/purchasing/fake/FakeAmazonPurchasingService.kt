package com.tatsuki.purchasing.fake

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.internal.model.UserDataResponseBuilder
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.RequestId
import com.amazon.device.iap.model.UserDataResponse.RequestStatus
import com.tatsuki.purchasing.core.AmazonPurchasingService

class FakeAmazonPurchasingService : AmazonPurchasingService {

  private var status: FakeServiceStatus = FakeServiceStatus.Available

  fun setup(status: FakeServiceStatus) {
    this.status = status
  }

  private lateinit var purchasingListener: PurchasingListener
  private var isEnablePendingPurchases = false

  // Amazon user account
  private var amazonUser = FakeAmazonUser()

  override fun registerListener(context: Context, listener: PurchasingListener) {
    this.purchasingListener = listener
  }

  override fun enablePendingPurchases() {
    isEnablePendingPurchases = true
  }

  override fun getUserData(): RequestId {
    val requestStatus = when (status) {
      FakeServiceStatus.Available -> {
        RequestStatus.SUCCESSFUL
      }

      FakeServiceStatus.Unavailable -> {
        RequestStatus.FAILED
      }
    }
    val userDataResponse = UserDataResponseBuilder()
      .apply {
        userData = amazonUser.userData
        requestId = RequestId()
        this.requestStatus = requestStatus
      }.build()
    purchasingListener.onUserDataResponse(userDataResponse)
    return userDataResponse.requestId
  }

  override fun getProductData(skus: Set<String>): RequestId {
    purchasingListener.onProductDataResponse(null)
    TODO("Not yet implemented")
  }

  override fun purchase(sku: String): RequestId {
    purchasingListener.onPurchaseResponse(null)
    TODO("Not yet implemented")
  }

  override fun notifyFulfillment(id: String, fulfillmentResult: FulfillmentResult) {
    TODO("Not yet implemented")
  }

  override fun getPurchaseUpdates(requestAll: Boolean): RequestId {
    purchasingListener.onPurchaseUpdatesResponse(null)
    TODO("Not yet implemented")
  }
}