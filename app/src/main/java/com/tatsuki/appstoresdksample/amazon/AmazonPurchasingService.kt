package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.Product
import com.amazon.device.iap.model.Receipt
import com.amazon.device.iap.model.UserData

interface AmazonPurchasingService {

  fun registerPurchasingService()

  suspend fun getUserData(): UserData

  suspend fun getProductData(productSkus: Set<String>): Map<String, Product>

  suspend fun purchase(productSku: String): Receipt

  fun notifyFulfillment(receiptId: String, fulfillmentResult: FulfillmentResult)
}