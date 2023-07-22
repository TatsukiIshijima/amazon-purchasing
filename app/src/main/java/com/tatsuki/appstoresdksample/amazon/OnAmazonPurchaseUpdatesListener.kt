package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.PurchaseUpdatesResponse

interface OnAmazonPurchaseUpdatesListener {

  fun onPurchaseUpdates(purchaseUpdatesResponse: PurchaseUpdatesResponse?)
}