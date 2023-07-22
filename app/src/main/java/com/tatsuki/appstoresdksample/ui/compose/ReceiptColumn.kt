package com.tatsuki.appstoresdksample.ui.compose

import androidx.annotation.StringRes
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
import com.tatsuki.appstoresdksample.R
import com.tatsuki.appstoresdksample.model.ReceiptItem

@Composable
fun ReceiptColumn(
  modifier: Modifier = Modifier,
  receiptItems: List<ReceiptItem>,
  onClickItem: (ReceiptItem) -> Unit,
) {
  LazyColumn(
    modifier = modifier
  ) {
    items(receiptItems.size) { index ->
      ReceiptItem(
        receiptItem = receiptItems[index],
        onClick = { onClickItem(receiptItems[index]) }
      )
    }
  }
}

@Composable
private fun ReceiptItem(
  modifier: Modifier = Modifier,
  receiptItem: ReceiptItem,
  onClick: (ReceiptItem) -> Unit,
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 4.dp, bottom = 4.dp)
      .clickable { onClick(receiptItem) }
  ) {
    Column {
      ReceiptItemRow(
        labelId = R.string.user_id_label,
        text = receiptItem.userId,
      )
      ReceiptItemRow(
        labelId = R.string.marketplace_label,
        text = receiptItem.marketplace
      )
      ReceiptItemRow(
        labelId = R.string.receipt_id_label,
        text = receiptItem.receiptId
      )
      ReceiptItemRow(
        labelId = R.string.receipt_sku_label,
        text = receiptItem.sku
      )
      ReceiptItemRow(
        labelId = R.string.receipt_product_type_label,
        text = receiptItem.productType
      )
      ReceiptItemRow(
        labelId = R.string.receipt_purchase_date_label,
        text = receiptItem.purchaseDate
      )
      ReceiptItemRow(
        labelId = R.string.receipt_cancel_date_label,
        text = receiptItem.cancelDate
      )
      ReceiptItemRow(
        labelId = R.string.receipt_deferred_date_label,
        text = receiptItem.deferredDate
      )
      ReceiptItemRow(
        labelId = R.string.receipt_deferred_sku_label,
        text = receiptItem.deferredSku
      )
      ReceiptItemRow(
        labelId = R.string.receipt_term_sku_label,
        text = receiptItem.termSku
      )
    }
  }
}

@Composable
private fun ReceiptItemRow(
  modifier: Modifier = Modifier,
  @StringRes labelId: Int,
  text: String,
) {
  Row(
    modifier = modifier
  ) {
    Text(
      text = stringResource(id = labelId),
      modifier = Modifier.weight(0.3f)
    )
    Text(
      text = text,
      modifier = Modifier.weight(0.7f)
    )
  }
}