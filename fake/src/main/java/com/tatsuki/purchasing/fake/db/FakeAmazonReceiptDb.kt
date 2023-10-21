package com.tatsuki.purchasing.fake.db

import com.tatsuki.purchasing.fake.model.FakeAmazonReceiptData

interface FakeAmazonReceiptDb {

  val receiptDataList: List<FakeAmazonReceiptData>

  fun add(receiptData: FakeAmazonReceiptData)

  fun getReceiptDataList(userId: String): List<FakeAmazonReceiptData>

  fun getReceiptData(receiptId: String): FakeAmazonReceiptData?

  fun remove(receiptId: String)

  fun clear()
}