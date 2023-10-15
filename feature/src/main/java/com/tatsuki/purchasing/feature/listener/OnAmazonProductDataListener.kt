package com.tatsuki.purchasing.feature.listener

import com.amazon.device.iap.model.ProductDataResponse

interface OnAmazonProductDataListener {

  fun onProductData(productDataResponse: ProductDataResponse?)
}