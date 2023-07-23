package com.tatsuki.amazonpurchasingsample.model

import com.amazon.device.iap.model.Product

data class ProductItem(
  val sku: String,
  val productType: String,
  val description: String,
  val price: String,
  val smallIconUrl: String,
  val title: String,
  val coinsReward: Int,
  val subscriptionPeriod: String,
  val freeTrialPeriod: String,
) {

  companion object {
    fun from(product: Product): ProductItem {
      return ProductItem(
        sku = product.sku,
        productType = product.productType.name,
        description = product.description,
        price = product.price ?: "Null",
        smallIconUrl = product.smallIconUrl,
        title = product.title,
        coinsReward = product.coinsReward?.amount ?: 0,
        subscriptionPeriod = product.subscriptionPeriod ?: "Null",
        freeTrialPeriod = product.freeTrialPeriod ?: "Null",
      )
    }
  }
}
