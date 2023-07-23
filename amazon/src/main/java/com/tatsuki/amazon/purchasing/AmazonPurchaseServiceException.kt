package com.tatsuki.amazon.purchasing

sealed class AmazonPurchaseServiceException : Exception() {

  object NotSupportedException : AmazonPurchaseServiceException()

  object GetUserDataFailedException : AmazonPurchaseServiceException()

  object GetProductDataFailedException : AmazonPurchaseServiceException()

  object PurchaseFailedException : AmazonPurchaseServiceException()

  object InvalidSkuException : AmazonPurchaseServiceException()

  object AlreadyPurchasedException : AmazonPurchaseServiceException()

  object PendingException : AmazonPurchaseServiceException()
}