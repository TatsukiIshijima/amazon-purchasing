package com.tatsuki.purchasing.feature.listener

import com.amazon.device.iap.model.PurchaseResponse

interface OnAmazonPurchaseListener {

  fun onPurchase(purchaseResponse: PurchaseResponse?)
}