package com.tatsuki.appstoresdksample.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tatsuki.appstoresdksample.model.ProductItem
import com.tatsuki.appstoresdksample.model.ReceiptItem
import com.tatsuki.appstoresdksample.ui.theme.AppStoreSDKSampleTheme

@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  header: @Composable () -> Unit,
  leftContent: @Composable () -> Unit,
  rightContent: @Composable () -> Unit,
) {
  Column(modifier = modifier.padding(24.dp)) {
    header()
    Spacer(modifier = Modifier.height(24.dp))
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxHeight()
          .padding(end = 12.dp)
      ) {
        leftContent()
      }
      Divider(
        modifier = Modifier
          .fillMaxHeight()
          .width(1.dp),
        color = Color.Black,
      )
      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxHeight()
          .padding(start = 12.dp)
      ) {
        rightContent()
      }
    }
  }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
private fun PreviewMainScreen() {
  val dummyProductItem = ProductItem(
    sku = "sku",
    productType = "productType",
    description = "description",
    price = "0",
    smallIconUrl = "",
    title = "title",
    coinsReward = 0,
    subscriptionPeriod = "",
    freeTrialPeriod = ""
  )
  val dummyReceiptItem = ReceiptItem(
    id = "id",
  )
  AppStoreSDKSampleTheme {
    Surface {
      MainScreen(
        header = {
          UserDataHeader(
            userId = "User1",
            marketplace = "JP"
          )
        },
        leftContent = {
          ProductBody(
            modifier = Modifier.fillMaxWidth(),
            productItems = (0..100).map { dummyProductItem },
            onClickItem = {}
          )
        },
        rightContent = {
          ReceiptBody(
            modifier = Modifier.fillMaxWidth(),
            receiptItems = (0..10).map { dummyReceiptItem })
        },
      )
    }
  }
}