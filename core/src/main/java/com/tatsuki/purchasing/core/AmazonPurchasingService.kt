package com.tatsuki.purchasing.core

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.RequestId

interface AmazonPurchasingService {

  fun registerListener(context: Context, listener: PurchasingListener)

  fun enablePendingPurchases()

  fun getUserData(): RequestId

  fun getProductData(skus: Set<String>): RequestId

  fun purchase(sku: String): RequestId

  fun notifyFulfillment(id: String, fulfillmentResult: FulfillmentResult)

  fun getPurchaseUpdates(requestAll: Boolean = false): RequestId
}