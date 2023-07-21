package com.tatsuki.appstoresdksample.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tatsuki.appstoresdksample.R
import com.tatsuki.appstoresdksample.model.ProductItem

@Composable
fun ProductBody(
  modifier: Modifier = Modifier,
  productItems: List<ProductItem>,
  onClickItem: (ProductItem) -> Unit,
) {
  Body(
    modifier = modifier,
    titleResource = R.string.product_list_title,
    column = {
      ProductColumn(
        productItems = productItems,
        onClickItem = { onClickItem(it) }
      )
    }
  )
}