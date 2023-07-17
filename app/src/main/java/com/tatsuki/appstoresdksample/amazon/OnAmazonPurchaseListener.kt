package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.PurchaseResponse

interface OnAmazonPurchaseListener {

  fun onPurchase(purchaseResponse: PurchaseResponse?)
}