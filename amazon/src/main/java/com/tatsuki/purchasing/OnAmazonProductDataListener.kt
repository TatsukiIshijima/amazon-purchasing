package com.tatsuki.purchasing

import com.amazon.device.iap.model.ProductDataResponse

interface OnAmazonProductDataListener {

  fun onProductData(productDataResponse: ProductDataResponse?)
}