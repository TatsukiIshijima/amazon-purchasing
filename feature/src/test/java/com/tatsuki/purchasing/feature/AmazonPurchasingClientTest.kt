package com.tatsuki.purchasing.feature

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tatsuki.purchasing.AmazonPurchasingClientImpl
import com.tatsuki.purchasing.fake.FakeAmazonPurchasingService
import com.tatsuki.purchasing.fake.FakeServiceStatus
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AmazonPurchasingClientTest {

  private lateinit var context: Context
  private lateinit var fakeAmazonPurchasingService: FakeAmazonPurchasingService
  private lateinit var amazonPurchasingClient: AmazonPurchasingClientImpl

  @Before
  fun setup() {
    context = ApplicationProvider.getApplicationContext()
    fakeAmazonPurchasingService = FakeAmazonPurchasingService()
    amazonPurchasingClient = AmazonPurchasingClientImpl(context, fakeAmazonPurchasingService)
    amazonPurchasingClient.registerPurchasingService()
  }

  @Test
  fun purchaseSubscription() = runTest {
    fakeAmazonPurchasingService.setup(FakeServiceStatus.Available)
    val amazonUserData = amazonPurchasingClient.getUserData()
    assert(amazonUserData.marketplace == "JP")
  }
}