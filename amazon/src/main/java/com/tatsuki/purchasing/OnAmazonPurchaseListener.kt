package com.tatsuki.purchasing

import com.amazon.device.iap.model.PurchaseResponse

interface OnAmazonPurchaseListener {

  fun onPurchase(purchaseResponse: PurchaseResponse?)
}