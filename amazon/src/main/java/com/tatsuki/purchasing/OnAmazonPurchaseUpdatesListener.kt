package com.tatsuki.purchasing

import com.amazon.device.iap.model.PurchaseUpdatesResponse

interface OnAmazonPurchaseUpdatesListener {

  fun onPurchaseUpdates(purchaseUpdatesResponse: PurchaseUpdatesResponse?)
}