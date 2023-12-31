package com.tatsuki.amazonpurchasingsample

object ProductSku {

  val list = HashSet<String>(
    listOf(
      // add parent sku here.
      "test.subscription.item.1",
      "test.subscription.item.2",
      "test.subscription.item.3",
      "test.subscription.item.4",
      "test.subscription.item.5",
      "test.subscription.item6",
      "test.subscription.item7",
      // add child(term) sku here.
      "test.subscription.item.1.monthly",
      "test.subscription.item.1.weekly",
      "test.subscription.item.2.monthly",
      "test.subscription.item.2.weekly",
      "test.subscription.item.3.monthly",
      "test.subscription.item.3.weekly",
      "test.subscription.item.4.weekly",
      "test.subscription.item.4.monthly",
      "test.subscription.item.5.weekly",
      "test.subscription.item.5.monthly",
      "test.subscription.item6.weekly",
      "test.subscription.item6.monthly",
      "test.subscription.item7.weekly",
      "test.subscription.item7.monthly",
    )
  )
}
