package com.tatsuki.amazonpurchasingsample.model

import com.amazon.device.iap.model.PromotionPlan

data class PromotionPlanItem(
  val promotionPricePeriod: String,
  val promotionPrice: String,
  val promotionPriceCycles: Int,
) {

  companion object {
    fun from(promotionPlan: PromotionPlan): PromotionPlanItem {
      return PromotionPlanItem(
        promotionPricePeriod = promotionPlan.promotionPricePeriod,
        promotionPrice = promotionPlan.promotionPrice,
        promotionPriceCycles = promotionPlan.promotionPriceCycles,
      )
    }
  }
}
