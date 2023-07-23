package com.tatsuki.appstoresdksample.model

import com.tatsuki.amazon.purchasing.AmazonPurchasedReceipt

data class ReceiptItem(
  val userId: String,
  val marketplace: String,
  val receiptId: String,
  val sku: String,
  val productType: String,
  val purchaseDate: String,
  val cancelDate: String,
  val deferredDate: String,
  val deferredSku: String,
  val termSku: String,
) {

  companion object {
    fun from(amazonPurchasedReceipt: AmazonPurchasedReceipt): ReceiptItem {
      return ReceiptItem(
        userId = amazonPurchasedReceipt.userData.userId,
        marketplace = amazonPurchasedReceipt.userData.marketplace,
        receiptId = amazonPurchasedReceipt.receipt.receiptId,
        sku = amazonPurchasedReceipt.receipt.sku,
        productType = amazonPurchasedReceipt.receipt.productType.name,
        purchaseDate = amazonPurchasedReceipt.receipt.purchaseDate.toString(),
        cancelDate = amazonPurchasedReceipt.receipt.cancelDate?.toString() ?: "Null",
        deferredDate = amazonPurchasedReceipt.receipt.deferredDate?.toString() ?: "Null",
        deferredSku = amazonPurchasedReceipt.receipt.deferredSku ?: "Null",
        termSku = amazonPurchasedReceipt.receipt.termSku ?: "Null"
      )
    }
  }
}
