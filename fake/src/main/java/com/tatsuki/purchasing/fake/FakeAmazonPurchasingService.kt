package com.tatsuki.purchasing.fake

import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.internal.model.ProductBuilder
import com.amazon.device.iap.internal.model.ProductDataResponseBuilder
import com.amazon.device.iap.internal.model.PurchaseResponseBuilder
import com.amazon.device.iap.internal.model.PurchaseUpdatesResponseBuilder
import com.amazon.device.iap.internal.model.UserDataBuilder
import com.amazon.device.iap.internal.model.UserDataResponseBuilder
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.Product
import com.amazon.device.iap.model.ProductDataResponse
import com.amazon.device.iap.model.ProductType
import com.amazon.device.iap.model.PurchaseResponse
import com.amazon.device.iap.model.PurchaseUpdatesResponse
import com.amazon.device.iap.model.RequestId
import com.amazon.device.iap.model.UserDataResponse
import com.tatsuki.purchasing.core.AmazonPurchasingService
import com.tatsuki.purchasing.fake.db.FakeAmazonReceiptDb
import com.tatsuki.purchasing.fake.model.FakeAmazonReceiptData
import com.tatsuki.purchasing.fake.model.FakeAmazonUser

class FakeAmazonPurchasingService(
  private val fakeAmazonReceiptDb: FakeAmazonReceiptDb
) : AmazonPurchasingService {

  private var status: FakeServiceStatus = FakeServiceStatus.Available

  fun setup(status: FakeServiceStatus) {
    this.status = status
  }

  private lateinit var purchasingListener: PurchasingListener
  private var isEnablePendingPurchases = false
  private var purchaseUpdatesPageNumber = 1
  private val purchaseUpdatesPageSize = 3

  // Current amazon user account
  private var amazonUser = FakeAmazonUser()

  // Sku list of subscription products defined in the Amazon developer console.
  private val subscriptionSkus = listOf(Consts.SUBSCRIPTION_SKU)

  // Sku list of in-app products defined in the Amazon Developer console.
  private val inAppSkus = listOf(Consts.IN_APP_SKU)

  // List of products defined in the Amazon Developer console.
  private val products = subscriptionSkus.mapIndexed { index, sku ->
    ProductBuilder()
      .apply {
        this.sku = sku
        price = "${1000 * (index + 1)}"
        title = "test_subscription_title_${index + 1}"
        description = "test_subscription_description_${index + 1}"
        productType = ProductType.SUBSCRIPTION
        smallIconUrl = "https://test.small.icon.url_${index + 1}"
      }.build()
  } + inAppSkus.mapIndexed { index, sku ->
    ProductBuilder()
      .apply {
        this.sku = sku
        price = "${100 * (index + 1)}"
        title = "test_in_app_title_${index + 1}"
        description = "test_in_app_description_${index + 1}"
        productType = ProductType.CONSUMABLE
        smallIconUrl = "https://test.small.icon.url_${index + 1}"
      }.build()
  }

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
      .setUserData(userData)
      .setRequestId(RequestId())
      .setRequestStatus(requestStatus)
      .build()
    purchasingListener.onUserDataResponse(userDataResponse)
    return userDataResponse.requestId
  }

  override fun getProductData(skus: Set<String>): RequestId {
    val (requestStatus, productData) = when (status) {
      FakeServiceStatus.Available -> {
        val productData = products
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
      .setProductData(productData)
      .setRequestId(RequestId())
      .setRequestStatus(requestStatus)
      .build()
    purchasingListener.onProductDataResponse(productDataResponseBuilder)
    return productDataResponseBuilder.requestId
  }

  override fun purchase(sku: String): RequestId {
    when (status) {
      FakeServiceStatus.Available -> {
        if (!products.any { it.sku == sku }) {
          val purchaseResponse = PurchaseResponseBuilder()
            .apply {
              requestId = RequestId()
              requestStatus = PurchaseResponse.RequestStatus.INVALID_SKU
            }.build()
          purchasingListener.onPurchaseResponse(purchaseResponse)
          return purchaseResponse.requestId
        }
        val targetProduct = products.first { it.sku == sku }
        val receiptData = FakeAmazonReceiptData.create(
          userId = amazonUser.userData.userId,
          sku = targetProduct.sku,
          productType = targetProduct.productType
        )
        fakeAmazonReceiptDb.add(receiptData)
        val purchaseResponse = PurchaseResponseBuilder()
          .setUserData(amazonUser.userData)
          .setRequestId(RequestId())
          .setRequestStatus(PurchaseResponse.RequestStatus.SUCCESSFUL)
          .setReceipt(receiptData.receipt)
          .build()
        purchasingListener.onPurchaseResponse(purchaseResponse)
        return purchaseResponse.requestId
      }

      FakeServiceStatus.Unavailable -> {
        val purchaseResponse = PurchaseResponseBuilder()
          .setRequestId(RequestId())
          .setRequestStatus(PurchaseResponse.RequestStatus.FAILED)
          .build()
        purchasingListener.onPurchaseResponse(purchaseResponse)
        return purchaseResponse.requestId
      }
    }
  }

  override fun notifyFulfillment(id: String, fulfillmentResult: FulfillmentResult) {
    val receiptData = fakeAmazonReceiptDb.getReceiptData(id) ?: return
    // Delete only consume(in-app) product as it will no longer return receipts.
    // https://developer.amazon.com/ja/docs/in-app-purchasing/iap-faqs.html#iap-api-questions
    if (receiptData.receipt.productType == ProductType.CONSUMABLE && fulfillmentResult == FulfillmentResult.FULFILLED) {
      fakeAmazonReceiptDb.remove(id)
    } else {
      fakeAmazonReceiptDb.update(
        receiptData.copy(
          fulfillmentResult = fulfillmentResult
        )
      )
    }
  }

  override fun getPurchaseUpdates(requestAll: Boolean): RequestId {
    when (status) {
      FakeServiceStatus.Available -> {
        val (userReceiptDataList, hasMore) = if (requestAll) {
          val userReceiptDataList =
            fakeAmazonReceiptDb.getReceiptDataList(amazonUser.userData.userId)
          userReceiptDataList to false
        } else {
          val receiptDataList = fakeAmazonReceiptDb.getReceiptDataList(amazonUser.userData.userId)
          val startIndex = (purchaseUpdatesPageNumber - 1) * purchaseUpdatesPageSize
          val endIndex = minOf(startIndex + purchaseUpdatesPageSize, receiptDataList.size)
          val hasMore = startIndex < receiptDataList.size
          if (hasMore) {
            purchaseUpdatesPageNumber++
          }
          val userReceiptDataList = receiptDataList.subList(startIndex, endIndex)
          userReceiptDataList to hasMore
        }

        val purchaseUpdatesResponse = PurchaseUpdatesResponseBuilder()
          .setRequestId(RequestId())
          .setRequestStatus(PurchaseUpdatesResponse.RequestStatus.FAILED)
          .setUserData(amazonUser.userData)
          .setHasMore(hasMore)
          .setReceipts(userReceiptDataList.map { it.receipt })
          .build()
        purchasingListener.onPurchaseUpdatesResponse(purchaseUpdatesResponse)
        return purchaseUpdatesResponse.requestId
      }

      FakeServiceStatus.Unavailable -> {
        val purchaseUpdatesResponse = PurchaseUpdatesResponseBuilder()
          .setRequestId(RequestId())
          .setRequestStatus(PurchaseUpdatesResponse.RequestStatus.FAILED)
          .build()
        purchasingListener.onPurchaseUpdatesResponse(purchaseUpdatesResponse)
        return purchaseUpdatesResponse.requestId
      }
    }
  }
}