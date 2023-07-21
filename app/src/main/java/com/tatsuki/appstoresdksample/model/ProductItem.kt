package com.tatsuki.appstoresdksample.model

import com.amazon.device.iap.model.Product

data class ProductItem(
  val sku: String,
  val productType: String,
  val description: String,
  val smallIconUrl: String,
  val title: String,
  val coinsReward: Int,
) {

  companion object {
    fun from(product: Product): ProductItem {
      return ProductItem(
        sku = product.sku,
        productType = product.productType.name,
        description = product.description,
        smallIconUrl = product.smallIconUrl,
        title = product.title,
        coinsReward = product.coinsReward?.amount ?: 0
      )
    }
  }
}
