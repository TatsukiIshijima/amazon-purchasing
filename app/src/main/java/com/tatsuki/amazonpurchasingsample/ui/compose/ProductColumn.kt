package com.tatsuki.amazonpurchasingsample.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
      ProductItemRow(
        labelId = R.string.product_sku_label,
        text = productItem.sku
      )
      ProductItemRow(
        labelId = R.string.product_type_label,
        text = productItem.productType
      )
      ProductItemRow(
        labelId = R.string.product_description_label,
        text = productItem.description
      )
      ProductItemRow(
        labelId = R.string.product_price_label,
        text = productItem.price
      )
      ProductItemRow(
        labelId = R.string.product_title_label,
        text = productItem.title
      )
      ProductItemRow(
        labelId = R.string.product_coins_reward_amount_label,
        text = productItem.coinsReward.toString()
      )
      ProductItemRow(
        labelId = R.string.product_subscription_period_label,
        text = productItem.subscriptionPeriod
      )
      ProductItemRow(
        labelId = R.string.product_free_trial_period_label,
        text = productItem.freeTrialPeriod
      )
    }
  }
}

@Composable
private fun ProductItemRow(
  modifier: Modifier = Modifier,
  @StringRes labelId: Int,
  text: String,
) {
  Row(
    modifier = modifier
  ) {
    Text(
      text = stringResource(id = labelId),
      modifier = Modifier.weight(0.3f),
      style = MaterialTheme.typography.labelMedium
    )
    Text(
      text = text,
      modifier = Modifier.weight(0.7f),
      style = MaterialTheme.typography.bodyMedium
    )
  }
}