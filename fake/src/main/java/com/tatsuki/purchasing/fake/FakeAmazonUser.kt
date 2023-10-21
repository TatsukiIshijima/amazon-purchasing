package com.tatsuki.purchasing.fake

import com.amazon.device.iap.internal.model.UserDataBuilder
import com.amazon.device.iap.model.UserData

data class FakeAmazonUser(
  val userData: UserData =
    UserDataBuilder()
      .apply {
        userId = generateRandomString(10)
        marketplace = "JP"
      }.build()
)
