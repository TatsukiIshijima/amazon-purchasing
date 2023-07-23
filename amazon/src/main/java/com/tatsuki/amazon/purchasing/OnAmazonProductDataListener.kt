package com.tatsuki.amazon.purchasing

import com.amazon.device.iap.model.ProductDataResponse

interface OnAmazonProductDataListener {

  fun onProductData(productDataResponse: ProductDataResponse?)
}