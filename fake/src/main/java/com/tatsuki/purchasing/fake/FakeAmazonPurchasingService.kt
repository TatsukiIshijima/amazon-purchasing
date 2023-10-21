package com.tatsuki.purchasing.fake

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.internal.model.ProductBuilder
import com.amazon.device.iap.internal.model.ProductDataResponseBuilder
import com.amazon.device.iap.internal.model.UserDataBuilder
import com.amazon.device.iap.internal.model.UserDataResponseBuilder
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.Product
import com.amazon.device.iap.model.ProductDataResponse
import com.amazon.device.iap.model.ProductType
import com.amazon.device.iap.model.RequestId
import com.amazon.device.iap.model.UserDataResponse
import com.tatsuki.purchasing.core.AmazonPurchasingService

class FakeAmazonPurchasingService : AmazonPurchasingService {

  private var status: FakeServiceStatus = FakeServiceStatus.Available

  fun setup(status: FakeServiceStatus) {
    this.status = status
  }

  private lateinit var purchasingListener: PurchasingListener
  private var isEnablePendingPurchases = false

  // Amazon user account
  private var amazonUser = FakeAmazonUser()

  override fun registerListener(context: Context, listener: PurchasingListener) {
    this.purchasingListener = listener
  }

  override fun enablePendingPurchases() {
    isEnablePendingPurchases = true
  }

  override fun getUserData(): RequestId {
    val (requestStatus, userData) = when (status) {
      FakeServiceStatus.Available -> {
        UserDataResponse.RequestStatus.SUCCESSFUL to amazonUser.userData
      }

      FakeServiceStatus.Unavailable -> {
        UserDataResponse.RequestStatus.FAILED to UserDataBuilder().build()
      }
    }
    val userDataResponse = UserDataResponseBuilder()
      .apply {
        this.userData = userData
        requestId = RequestId()
        this.requestStatus = requestStatus
      }.build()
    purchasingListener.onUserDataResponse(userDataResponse)
    return userDataResponse.requestId
  }

  override fun getProductData(skus: Set<String>): RequestId {
    val (requestStatus, productData) = when (status) {
      FakeServiceStatus.Available -> {
        val subscriptionProduct = ProductBuilder()
          .apply {
            sku = Consts.SUBSCRIPTION_SKU
            price = "1000"
            title = "test_subscription_title"
            description = "test_subscription_description"
            productType = ProductType.SUBSCRIPTION
            smallIconUrl = "https://"
          }.build()
        val inAppProduct = ProductBuilder()
          .apply {
            sku = Consts.IN_APP_SKU
            price = "100"
            title = "test_in_app_title"
            description = "test_in_app_description"
            productType = ProductType.CONSUMABLE
            smallIconUrl = "https://"
          }.build()
        val productData = listOf(subscriptionProduct, inAppProduct)
          .filter { product ->
            skus.any { sku ->
              product.sku == sku
            }
          }.map { product ->
            product.sku to product
          }.toMap()
        ProductDataResponse.RequestStatus.SUCCESSFUL to productData
      }

      FakeServiceStatus.Unavailable -> {
        ProductDataResponse.RequestStatus.FAILED to emptyMap<String, Product>()
      }
    }
    val productDataResponseBuilder = ProductDataResponseBuilder()
      .apply {
        this.productData = productData
        requestId = RequestId()
        this.requestStatus = requestStatus
      }.build()
    purchasingListener.onProductDataResponse(productDataResponseBuilder)
    return productDataResponseBuilder.requestId
  }

  override fun purchase(sku: String): RequestId {
    purchasingListener.onPurchaseResponse(null)
    TODO("Not yet implemented")
  }

  override fun notifyFulfillment(id: String, fulfillmentResult: FulfillmentResult) {
    TODO("Not yet implemented")
  }

  override fun getPurchaseUpdates(requestAll: Boolean): RequestId {
    purchasingListener.onPurchaseUpdatesResponse(null)
    TODO("Not yet implemented")
  }
}