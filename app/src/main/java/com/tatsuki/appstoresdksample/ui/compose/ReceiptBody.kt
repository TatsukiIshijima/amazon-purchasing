package com.tatsuki.appstoresdksample.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tatsuki.appstoresdksample.R
import com.tatsuki.appstoresdksample.model.ReceiptItem

@Composable
fun ReceiptBody(
  modifier: Modifier = Modifier,
  receiptItems: List<ReceiptItem>,
  onClickItem: (ReceiptItem) -> Unit,
) {
  Body(
    modifier = modifier,
    titleResource = R.string.receipt_list_title,
    column = {
      ReceiptColumn(
        receiptItems = receiptItems,
        onClickItem = { onClickItem(it) }
      )
    }
  )
}