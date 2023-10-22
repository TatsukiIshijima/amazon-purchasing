package com.tatsuki.amazonpurchasingsample.module

import android.content.Context
import com.tatsuki.purchasing.AmazonPurchasingClient
import com.tatsuki.purchasing.AmazonPurchasingClientImpl
import com.tatsuki.purchasing.AmazonPurchasingServiceImpl
import com.tatsuki.purchasing.core.AmazonPurchasingService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AmazonModule {

  @Provides
  @Singleton
  fun provideAmazonPurchasingService(): AmazonPurchasingService {
    return AmazonPurchasingServiceImpl()
  }

  @Provides
  @Singleton
  fun provideAmazonPurchasingClient(
    @ApplicationContext context: Context,
    purchasingService: AmazonPurchasingService
  ): AmazonPurchasingClient {
    return AmazonPurchasingClientImpl(context, purchasingService)
  }
}