package com.tatsuki.amazonpurchasingsample

object ProductSku {

  private const val SUBSCRIPTION_ITEM_WEAKLY_TEST_WITHOUT_TRIAL_SKU = "weekly_sku"
  private const val SUBSCRIPTION_ITEM_WEAKLY_TEST_WITH_TRIAL_SKU = "weekly_sku_with_trial"
  private const val SUBSCRIPTION_ITEM_MONTHLY_TEST_WITHOUT_TRIAL_SKU = "monthly_sku_without_trial"
  private const val SUBSCRIPTION_ITEM_MONTHLY_TEST_WITH_TRIAL_SKU = "monthly_sku_with_trial"
  private const val MONTHLY_DRAMA_SKU = "monthly_drama_sku"
  private const val YEARLY_DRAMA_SKU = "yearly_drama_sku"
  private const val MONTHLY_SOCCER_SKU = "monthly_soccer_sku"

  // スペルミスしたのでそのまま
  private const val MONTHLY_TEST_WITH_DISCOUNT_AND_TRIAL =
    "montlhly_test_with_discount_1m_with_trial"
  private const val MONTHLY_TEST_WITH_DISCOUNT = "monthly_test_with_discount_1m"

  val list = HashSet<String>(
    listOf(
      // add child sku here
      SUBSCRIPTION_ITEM_WEAKLY_TEST_WITHOUT_TRIAL_SKU,
      SUBSCRIPTION_ITEM_WEAKLY_TEST_WITH_TRIAL_SKU,
      SUBSCRIPTION_ITEM_MONTHLY_TEST_WITHOUT_TRIAL_SKU,
      SUBSCRIPTION_ITEM_MONTHLY_TEST_WITH_TRIAL_SKU,
      MONTHLY_DRAMA_SKU,
      YEARLY_DRAMA_SKU,
      MONTHLY_SOCCER_SKU,
      MONTHLY_TEST_WITH_DISCOUNT_AND_TRIAL,
      MONTHLY_TEST_WITH_DISCOUNT,
    )
  )
}
