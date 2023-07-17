package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.UserData

interface AmazonPurchasingService {

  fun registerPurchasingService()

  suspend fun getUserData(): UserData
}