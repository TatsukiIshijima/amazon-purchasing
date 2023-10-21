package com.tatsuki.purchasing.fake.model

import com.amazon.device.iap.internal.model.UserDataBuilder
import com.amazon.device.iap.model.UserData
import com.tatsuki.purchasing.fake.generateRandomString

data class FakeAmazonUser(
  val userData: UserData =
    UserDataBuilder()
      .apply {
        userId = generateRandomString(10)
        marketplace = "JP"
      }.build()
)
