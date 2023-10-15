package com.tatsuki.amazonpurchasingsample.module

import android.content.Context
import com.tatsuki.purchasing.AmazonPurchasingClient
import com.tatsuki.purchasing.AmazonPurchasingClientImpl

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
  fun provideAmazonPurchasingClient(
    @ApplicationContext context: Context,
  ): AmazonPurchasingClient {
    return AmazonPurchasingClientImpl(context)
  }
}