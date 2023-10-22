package com.tatsuki.purchasing.fake

import com.amazon.device.iap.internal.model.ProductBuilder
import com.amazon.device.iap.model.ProductType

object Consts {
  const val SUBSCRIPTION_SKU_PREFIX = "subscription_sku"
  private const val SUBSCRIPTION_PRODUCT_TITLE_PREFIX = "test_subscription_title"
  private const val SUBSCRIPTION_PRODUCT_DESCRIPTION_PREFIX = "test_subscription_description"
  private const val SUBSCRIPTION_PRODUCT_SMALL_ICON_URL_PREFIX = "https://test.small.icon.url"

  private const val IN_APP_SKU_PREFIX = "in_app_sku"
  private const val IN_APP_PRODUCT_TITLE_PREFIX = "test_in_app_title"
  private const val IN_APP_PRODUCT_DESCRIPTION_PREFIX = "test_in_app_description"
  private const val IN_APP_PRODUCT_SMALL_ICON_URL_PREFIX = "https://test.small.icon.url"

  // Sku list of subscription products.
  private val SUBSCRIPTION_SKUS = (0..4).map {
    "${SUBSCRIPTION_SKU_PREFIX}_${it + 1}"
  }

  // Sku list of in-app products.
  private val IN_APP_SKUS = (0..4).map {
    "${IN_APP_SKU_PREFIX}_${it + 1}"
  }

  // List of all products.
  val ALL_PRODUCTS =
    SUBSCRIPTION_SKUS.mapIndexed { index, sku ->
      ProductBuilder()
        .setSku(sku)
        .setPrice("${1000 * (index + 1)}")
        .setTitle("${SUBSCRIPTION_PRODUCT_TITLE_PREFIX}_${index + 1}")
        .setDescription("${SUBSCRIPTION_PRODUCT_DESCRIPTION_PREFIX}_${index + 1}")
        .setProductType(ProductType.SUBSCRIPTION)
        .setSmallIconUrl("${SUBSCRIPTION_PRODUCT_SMALL_ICON_URL_PREFIX}_${index + 1}")
        .build()
    } + IN_APP_SKUS.mapIndexed { index, sku ->
      ProductBuilder()
        .setSku(sku)
        .setPrice("${100 * (index + 1)}")
        .setTitle("${IN_APP_PRODUCT_TITLE_PREFIX}_${index + 1}")
        .setDescription("${IN_APP_PRODUCT_DESCRIPTION_PREFIX}_${index + 1}")
        .setProductType(ProductType.CONSUMABLE)
        .setSmallIconUrl("${IN_APP_PRODUCT_SMALL_ICON_URL_PREFIX}_${index + 1}")
        .build()
    }
}