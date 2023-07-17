package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.ProductDataResponse

interface OnAmazonProductDataListener {

  fun onProductData(productDataResponse: ProductDataResponse?)
}