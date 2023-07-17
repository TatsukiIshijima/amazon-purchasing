package com.tatsuki.appstoresdksample.amazon

sealed class AmazonPurchaseException : Exception() {

  object NotSupportedException : AmazonPurchaseException()

  object GetUserDataFailedException : AmazonPurchaseException()
}