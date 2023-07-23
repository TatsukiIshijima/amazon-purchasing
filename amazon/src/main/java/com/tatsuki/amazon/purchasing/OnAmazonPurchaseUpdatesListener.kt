package com.tatsuki.amazon.purchasing

import com.amazon.device.iap.model.PurchaseUpdatesResponse

interface OnAmazonPurchaseUpdatesListener {

  fun onPurchaseUpdates(purchaseUpdatesResponse: PurchaseUpdatesResponse?)
}