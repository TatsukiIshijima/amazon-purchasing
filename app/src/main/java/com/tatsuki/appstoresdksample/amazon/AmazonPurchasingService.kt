package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.Product
import com.amazon.device.iap.model.UserData

interface AmazonPurchasingService {

  fun registerPurchasingService()

  suspend fun getUserData(): UserData

  suspend fun getProductData(productSkus: Set<String>): Map<String, Product>

  suspend fun purchase(productSku: String): AmazonPurchasedReceipt

  fun notifyFulfillment(receiptId: String, fulfillmentResult: FulfillmentResult)

  suspend fun getPurchaseUpdates(): List<AmazonPurchasedReceipt>
}