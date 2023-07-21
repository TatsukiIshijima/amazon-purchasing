package com.tatsuki.appstoresdksample.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tatsuki.appstoresdksample.ProductItem
import com.tatsuki.appstoresdksample.R

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
      .padding(top = 4.dp, bottom = 4.dp)
      .clickable { onClick(productItem) }
  ) {
    Row {
      Column(
        modifier = Modifier.weight(0.2f)
      ) {
        Text(text = stringResource(id = R.string.product_sku_label))
        Text(text = stringResource(id = R.string.product_type_label))
        Text(text = stringResource(id = R.string.product_description_label))
        Text(text = stringResource(id = R.string.product_title_label))
        Text(text = stringResource(id = R.string.product_coins_reward_amount_label))
      }
      Column(
        modifier = Modifier.weight(0.8f)
      ) {
        Text(text = productItem.sku)
        Text(text = productItem.productType)
        Text(text = productItem.description)
        Text(text = productItem.title)
        Text(text = productItem.coinsReward.toString())
      }
    }
  }
}