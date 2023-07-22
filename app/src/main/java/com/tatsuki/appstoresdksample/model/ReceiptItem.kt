package com.tatsuki.appstoresdksample.model

import com.amazon.device.iap.model.Receipt

data class ReceiptItem(
  val id: String,
  val productType: String,
  val purchaseDate: String,
  val cancelDate: String,
  val deferredDate: String,
  val deferredSku: String,
  val termSku: String,
) {

  companion object {
    fun from(receipt: Receipt): ReceiptItem {
      return ReceiptItem(
        id = receipt.receiptId,
        productType = receipt.productType.name,
        purchaseDate = receipt.purchaseDate.toString(),
        cancelDate = receipt.cancelDate.toString(),
        deferredDate = receipt.deferredDate.toString(),
        deferredSku = receipt.deferredSku,
        termSku = receipt.termSku
      )
    }
  }
}
