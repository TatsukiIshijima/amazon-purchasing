package com.tatsuki.purchasing.fake

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.RequestId
import com.tatsuki.purchasing.core.AmazonPurchasingService

class FakeAmazonPurchasingService : AmazonPurchasingService {

  override fun registerListener(context: Context, listener: PurchasingListener) {
    TODO("Not yet implemented")
  }

  override fun enablePendingPurchases() {
    TODO("Not yet implemented")
  }

  override fun getUserData(): RequestId {
    TODO("Not yet implemented")
  }

  override fun getProductData(skus: Set<String>): RequestId {
    TODO("Not yet implemented")
  }

  override fun purchase(sku: String): RequestId {
    TODO("Not yet implemented")
  }

  override fun notifyFulfillment(id: String, fulfillmentResult: FulfillmentResult) {
    TODO("Not yet implemented")
  }

  override fun getPurchaseUpdates(requestAll: Boolean): RequestId {
    TODO("Not yet implemented")
  }
}