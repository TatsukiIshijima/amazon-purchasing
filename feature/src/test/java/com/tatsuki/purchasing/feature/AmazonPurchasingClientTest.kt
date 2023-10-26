package com.tatsuki.purchasing.feature

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.amazon.device.iap.model.FulfillmentResult
import com.tatsuki.purchasing.AmazonPurchasingClientImpl
import com.tatsuki.purchasing.fake.Consts
import com.tatsuki.purchasing.fake.FakeAmazonPurchasingService
import com.tatsuki.purchasing.fake.FakeServiceStatus
import com.tatsuki.purchasing.fake.db.FakeAmazonReceiptDb
import com.tatsuki.purchasing.fake.db.FakeAmazonReceiptDbClient
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AmazonPurchasingClientTest {

  private lateinit var context: Context
  private lateinit var fakeAmazonReceiptDb: FakeAmazonReceiptDb
  private lateinit var fakeAmazonPurchasingService: FakeAmazonPurchasingService
  private lateinit var amazonPurchasingClient: AmazonPurchasingClientImpl

  @Before
  fun setup() {
    context = ApplicationProvider.getApplicationContext()
    fakeAmazonReceiptDb = FakeAmazonReceiptDbClient()
    fakeAmazonPurchasingService = FakeAmazonPurchasingService(fakeAmazonReceiptDb)
    amazonPurchasingClient = AmazonPurchasingClientImpl(context, fakeAmazonPurchasingService)
    amazonPurchasingClient.registerPurchasingService()
  }

  @Test
  fun purchaseSubscription() = runTest {
    fakeAmazonPurchasingService.setup(FakeServiceStatus.Available)

    // Get current amazon account user
    val amazonUserData = amazonPurchasingClient.getUserData()
    assert(amazonUserData.marketplace == "JP")

    // Get product data
    val targetSku1 = "${Consts.SUBSCRIPTION_SKU_PREFIX}_1"
    val targetSku2 = "${Consts.SUBSCRIPTION_SKU_PREFIX}_2"
    val productData = amazonPurchasingClient.getProductData(setOf(targetSku1, targetSku2))
    assert(productData.size == 2)
    assert(productData[targetSku1]?.sku == targetSku1)
    assert(productData[targetSku2]?.sku == targetSku2)

    // Select a product to purchase and purchase it.
    val purchasedReceipt1 = amazonPurchasingClient.purchase(productData[targetSku1]!!.sku)
    // Fulfill the purchase.
    amazonPurchasingClient.notifyFulfillment(
      purchasedReceipt1.receipt.receiptId,
      FulfillmentResult.FULFILLED
    )
    val receiptData = fakeAmazonReceiptDb.getReceiptData(purchasedReceipt1.receipt.receiptId)
    assert(receiptData?.fulfillmentResult == FulfillmentResult.FULFILLED)

    // Get receipts.
    val purchaseUpdatesFirstTime = amazonPurchasingClient.getPurchaseUpdates()
    assert(purchaseUpdatesFirstTime.size == 1)
    assert(purchaseUpdatesFirstTime.first().receipt.receiptId == purchasedReceipt1.receipt.receiptId)

    // Get receipts again.
    // However, no value is returned because getPurchaseUpdates has been called previously.
    val purchaseUpdatesSecondTime = amazonPurchasingClient.getPurchaseUpdates()
    assert(purchaseUpdatesSecondTime.isEmpty())

    // Purchase subscription product again.
    val purchasedReceipt2 = amazonPurchasingClient.purchase(productData[targetSku2]!!.sku)

    // Get receipts again.
    val purchaseUpdatesThirdTime = amazonPurchasingClient.getPurchaseUpdates()
    assert(purchaseUpdatesThirdTime.size == 1)
    assert(purchaseUpdatesThirdTime.first().receipt.receiptId == purchasedReceipt2.receipt.receiptId)
  }

  @Test
  fun purchaseConsumableItem() = runTest {
    fakeAmazonPurchasingService.setup(FakeServiceStatus.Available)

    // Get current amazon account user
    val amazonUserData = amazonPurchasingClient.getUserData()
    assert(amazonUserData.marketplace == "JP")

    // Get product data
    val targetSku1 = "${Consts.IN_APP_SKU_PREFIX}_1"
    val targetSku2 = "${Consts.IN_APP_SKU_PREFIX}_2"
    val productData = amazonPurchasingClient.getProductData(setOf(targetSku1, targetSku2))
    assert(productData.size == 2)
    assert(productData[targetSku1]?.sku == targetSku1)
    assert(productData[targetSku2]?.sku == targetSku2)

    // Select a product to purchase and purchase it.
    val purchasedReceipt1 = amazonPurchasingClient.purchase(productData[targetSku1]!!.sku)
    // Fulfill the purchase.
    amazonPurchasingClient.notifyFulfillment(
      purchasedReceipt1.receipt.receiptId,
      FulfillmentResult.FULFILLED
    )

    // Get receipts. (Consumable items are not returned if transaction complete by called notifyFulfilment.)
    // https://developer.amazon.com/ja/docs/in-app-purchasing/iap-implement-iap.html#getpurchaseupdates-responses
    val purchaseUpdatesFirstTime = amazonPurchasingClient.getPurchaseUpdates()
    assert(purchaseUpdatesFirstTime.isEmpty())

    // Purchase consumable item again but not fulfill.
    val purchasedReceipt2 = amazonPurchasingClient.purchase(productData[targetSku2]!!.sku)

    // Get receipts. (Consumable items are returned if transaction not complete.)
    val purchaseUpdatesSecondTime = amazonPurchasingClient.getPurchaseUpdates()
    assert(purchaseUpdatesSecondTime.size == 1)
    assert(purchaseUpdatesSecondTime.first().receipt.receiptId == purchasedReceipt2.receipt.receiptId)
  }
}