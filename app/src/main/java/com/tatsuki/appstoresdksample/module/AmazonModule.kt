package com.tatsuki.appstoresdksample.module

import android.content.Context
import com.tatsuki.appstoresdksample.amazon.AmazonPurchasingService
import com.tatsuki.appstoresdksample.amazon.AmazonPurchasingServiceImpl
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
  fun provideAmazonPurchasingService(
    @ApplicationContext context: Context,
  ): AmazonPurchasingService {
    return AmazonPurchasingServiceImpl(context)
  }
}