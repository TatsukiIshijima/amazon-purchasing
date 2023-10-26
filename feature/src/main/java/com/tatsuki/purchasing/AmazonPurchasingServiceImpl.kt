package com.tatsuki.purchasing

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.PurchasingService
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.RequestId
import com.tatsuki.purchasing.core.AmazonPurchasingService

class AmazonPurchasingServiceImpl : AmazonPurchasingService {

  override fun registerListener(context: Context, listener: PurchasingListener) {
    PurchasingService.registerListener(context, listener)
  }

  override fun enablePendingPurchases() {
    PurchasingService.enablePendingPurchases()
  }

  override fun getUserData(): RequestId {
    return PurchasingService.getUserData()
  }

  override fun getProductData(skus: Set<String>): RequestId {
    return PurchasingService.getProductData(skus)
  }

  override fun purchase(sku: String): RequestId {
    return PurchasingService.purchase(sku)
  }

  override fun notifyFulfillment(id: String, fulfillmentResult: FulfillmentResult) {
    PurchasingService.notifyFulfillment(id, fulfillmentResult)
  }

  override fun getPurchaseUpdates(requestAll: Boolean): RequestId {
    return PurchasingService.getPurchaseUpdates(requestAll)
  }
}