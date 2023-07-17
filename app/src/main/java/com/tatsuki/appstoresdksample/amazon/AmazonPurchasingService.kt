package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.Product
import com.amazon.device.iap.model.UserData

interface AmazonPurchasingService {

  fun registerPurchasingService()

  suspend fun getUserData(): UserData

  suspend fun getProductData(productSkus: Set<String>): Map<String, Product>
}