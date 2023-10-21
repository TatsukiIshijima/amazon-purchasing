package com.tatsuki.purchasing.fake.db

import com.tatsuki.purchasing.fake.model.FakeAmazonReceiptData

class FakeAmazonReceiptDbClient : FakeAmazonReceiptDb {

  private val mutableReceiptDataList = mutableListOf<FakeAmazonReceiptData>()

  override val receiptDataList: List<FakeAmazonReceiptData> = mutableReceiptDataList

  override fun add(receiptData: FakeAmazonReceiptData) {
    mutableReceiptDataList.add(receiptData)
  }

  override fun getReceiptDataList(userId: String): List<FakeAmazonReceiptData> {
    return mutableReceiptDataList.filter { it.userId == userId }
  }

  override fun getReceiptData(receiptId: String): FakeAmazonReceiptData? {
    return mutableReceiptDataList.firstOrNull {
      it.receipt.receiptId == receiptId
    }
  }

  override fun remove(receiptId: String) {
    val targetReceiptData = mutableReceiptDataList.firstOrNull {
      it.receipt.receiptId == receiptId
    }
    if (targetReceiptData != null) {
      mutableReceiptDataList.remove(targetReceiptData)
    }
  }

  override fun clear() {
    mutableReceiptDataList.clear()
  }
}