package com.tatsuki.amazon.purchasing

sealed class AmazonPurchaseServiceException(
  override val message: String,
  open val statusCode: Int,
) : Exception(message) {

  class NotSupportedException(
    override val message: String = "This feature is not supported.",
    override val statusCode: Int,
  ) : AmazonPurchaseServiceException(message, statusCode)

  class GetUserDataFailedException(
    override val message: String = "Get user data failed.",
    override val statusCode: Int,
  ) : AmazonPurchaseServiceException(message, statusCode)

  class GetProductDataFailedException(
    override val message: String = "Get product data failed.",
    override val statusCode: Int
  ) : AmazonPurchaseServiceException(message, statusCode)

  class PurchaseFailedException(
    override val message: String = "Purchase failed.",
    override val statusCode: Int
  ) : AmazonPurchaseServiceException(message, statusCode)

  class InvalidSkuException(
    override val message: String = "Purchase failed. You have selected an invalid sku.",
    override val statusCode: Int
  ) : AmazonPurchaseServiceException(message, statusCode)

  class AlreadyPurchasedException(
    override val message: String = "Purchase failed. You have already Purchased this sku.",
    override val statusCode: Int
  ) : AmazonPurchaseServiceException(message, statusCode)

  class PendingException(
    override val message: String = "Purchase failed. You have a pending purchase.",
    override val statusCode: Int
  ) : AmazonPurchaseServiceException(message, statusCode)

  class PurchaseUpdatesFailedException(
    override val message: String = "Purchase updates failed.",
    override val statusCode: Int
  ) : AmazonPurchaseServiceException(message, statusCode)

  companion object {
    const val DEFAULT_STATUS_CODE = -1
  }
}