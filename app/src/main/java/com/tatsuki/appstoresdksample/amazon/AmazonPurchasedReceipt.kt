package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.Receipt
import com.amazon.device.iap.model.UserData

data class AmazonPurchasedReceipt(
  val userData: UserData,
  val receipt: Receipt,
)
