package com.tatsuki.amazonpurchasingsample.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tatsuki.amazonpurchasingsample.R
import com.tatsuki.amazonpurchasingsample.model.ProductItem

@Composable
fun ProductColumn(
  modifier: Modifier = Modifier,
  productItems: List<ProductItem>,
  onClickItem: (ProductItem) -> Unit,
) {
  LazyColumn(
    modifier = modifier,
  ) {
    items(productItems.size) { index ->
      ProductItem(
        productItem = productItems[index],
        onClick = { onClickItem(productItems[index]) }
      )
    }
  }
}

@Composable
private fun ProductItem(
  modifier: Modifier = Modifier,
  productItem: ProductItem,
  onClick: (ProductItem) -> Unit,
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 8.dp, bottom = 8.dp)
      .clickable { onClick(productItem) }
  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      CommonItemRow(
        labelId = R.string.product_sku_label,
        text = productItem.sku
      )
      CommonItemRow(
        labelId = R.string.product_type_label,
        text = productItem.productType
      )
      CommonItemRow(
        labelId = R.string.product_description_label,
        text = productItem.description
      )
      CommonItemRow(
        labelId = R.string.product_price_label,
        text = productItem.price
      )
      CommonItemRow(
        labelId = R.string.product_title_label,
        text = productItem.title
      )
      CommonItemRow(
        labelId = R.string.product_coins_reward_amount_label,
        text = productItem.coinsReward.toString()
      )
      CommonItemRow(
        labelId = R.string.product_subscription_period_label,
        text = productItem.subscriptionPeriod
      )
      CommonItemRow(
        labelId = R.string.product_free_trial_period_label,
        text = productItem.freeTrialPeriod
      )
      repeat(productItem.promotions.size) { index ->
        PromotionColumn(promotionItem = productItem.promotions[index])
      }
    }
  }
}
