package com.tatsuki.purchasing.fake.model

import com.amazon.device.iap.internal.model.ReceiptBuilder
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.ProductType
import com.amazon.device.iap.model.Receipt
import com.tatsuki.purchasing.fake.generateRandomString
import java.util.Date

data class FakeAmazonReceiptData(
  val userId: String,
  val receipt: Receipt,
  val fulfillmentResult: FulfillmentResult,
) {

  companion object {
    fun create(
      userId: String,
      sku: String,
      productType: ProductType,
      purchaseDate: Date = Date(),
    ): FakeAmazonReceiptData {
      return FakeAmazonReceiptData(
        userId = userId,
        receipt = ReceiptBuilder()
          .setReceiptId(generateRandomString(15))
          .setSku(sku)
          .setProductType(productType)
          .setPurchaseDate(purchaseDate)
          .build(),
        fulfillmentResult = FulfillmentResult.UNAVAILABLE
      )
    }
  }
}
