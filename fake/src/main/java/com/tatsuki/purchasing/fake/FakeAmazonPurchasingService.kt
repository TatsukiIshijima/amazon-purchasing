package com.tatsuki.purchasing.fake

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.RequestId
import com.tatsuki.purchasing.core.AmazonPurchasingService

class FakeAmazonPurchasingService : AmazonPurchasingService {

  private lateinit var purchasingListener: PurchasingListener
  private var isEnablePendingPurchases = false

  override fun registerListener(context: Context, listener: PurchasingListener) {
    this.purchasingListener = listener
  }

  override fun enablePendingPurchases() {
    isEnablePendingPurchases = true
  }

  override fun getUserData(): RequestId {
    val requestId = RequestId()
    purchasingListener.onUserDataResponse(null)
    return requestId
    TODO("Not yet implemented")
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