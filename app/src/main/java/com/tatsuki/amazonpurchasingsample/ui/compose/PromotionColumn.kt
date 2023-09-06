package com.tatsuki.amazonpurchasingsample.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.tatsuki.amazonpurchasingsample.R
import com.tatsuki.amazonpurchasingsample.model.PromotionItem
import com.tatsuki.amazonpurchasingsample.model.PromotionPlanItem
import com.tatsuki.amazonpurchasingsample.ui.theme.AppStoreSDKSampleTheme

@Composable
fun PromotionColumn(
  modifier: Modifier = Modifier,
  promotionItem: PromotionItem,
) {
  Row(
    modifier = modifier
  ) {
    PromotionsHeader(
      modifier = Modifier.weight(0.3f)
    )
    Column(
      modifier = Modifier.weight(0.7f)
    ) {
      CommonItemRow(
        labelId = R.string.promotion_type_label,
        text = promotionItem.promotionType
      )
      Row {
        Text(
          text = stringResource(id = R.string.promotion_plans_label),
          modifier = Modifier.weight(0.3f),
          style = MaterialTheme.typography.labelMedium,
        )
        Column(
          modifier = Modifier.weight(0.7f)
        ) {
          repeat(promotionItem.promotionPlans.size) { index ->
            PromotionPlanItem(
              promotionPlanItem = promotionItem.promotionPlans[index]
            )
          }
        }
      }
    }
  }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
private fun PreviewPromotionColumn() {
  AppStoreSDKSampleTheme {
    Surface {
      PromotionColumn(
        modifier = Modifier.fillMaxWidth(),
        promotionItem = PromotionItem(
          promotionType = "promotionType",
          promotionPlans = listOf(
            PromotionPlanItem(
              promotionPricePeriod = "promotionPricePeriod",
              promotionPrice = "promotionPrice",
              promotionPriceCycles = 1
            )
          )
        )
      )
    }
  }
}

@Composable
private fun PromotionsHeader(
  modifier: Modifier,
) {
  Text(
    text = stringResource(id = R.string.promotions_label),
    modifier = modifier,
    style = MaterialTheme.typography.labelMedium,
  )
}

@Composable
private fun PromotionPlanItem(
  modifier: Modifier = Modifier,
  promotionPlanItem: PromotionPlanItem,
) {
  Column(modifier = modifier) {
    CommonItemRow(
      labelId = R.string.promotion_price_period_label,
      text = promotionPlanItem.promotionPricePeriod,
      weight = 0.6f,
    )
    CommonItemRow(
      labelId = R.string.promotion_price_label,
      text = promotionPlanItem.promotionPrice,
      weight = 0.6f,
    )
    CommonItemRow(
      labelId = R.string.promotion_price_cycles_label,
      text = promotionPlanItem.promotionPriceCycles.toString(),
      weight = 0.6f,
    )
  }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
private fun PreviewPromotionPlanItem() {
  AppStoreSDKSampleTheme {
    Surface {
      PromotionPlanItem(
        promotionPlanItem = PromotionPlanItem(
          promotionPricePeriod = "promotionPricePeriod",
          promotionPrice = "promotionPrice",
          promotionPriceCycles = 1
        )
      )
    }
  }
}
