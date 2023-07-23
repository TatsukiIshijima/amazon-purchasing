package com.tatsuki.amazon.purchasing

import com.amazon.device.iap.model.PurchaseResponse

interface OnAmazonPurchaseListener {

  fun onPurchase(purchaseResponse: PurchaseResponse?)
}