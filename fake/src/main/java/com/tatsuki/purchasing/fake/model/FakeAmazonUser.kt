package com.tatsuki.purchasing.fake.model

import com.amazon.device.iap.internal.model.UserDataBuilder
import com.amazon.device.iap.model.UserData
import com.tatsuki.purchasing.fake.generateRandomString

data class FakeAmazonUser(
  val userData: UserData =
    UserDataBuilder()
      .setUserId(generateRandomString(10))
      .setMarketplace("JP")
      .build()
)
