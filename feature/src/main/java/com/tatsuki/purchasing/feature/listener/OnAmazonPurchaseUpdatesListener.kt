package com.tatsuki.purchasing.feature.listener

import com.amazon.device.iap.model.PurchaseUpdatesResponse

interface OnAmazonPurchaseUpdatesListener {

  fun onPurchaseUpdates(purchaseUpdatesResponse: PurchaseUpdatesResponse?)
}