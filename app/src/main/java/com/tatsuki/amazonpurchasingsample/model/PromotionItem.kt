package com.tatsuki.amazonpurchasingsample.model

import com.amazon.device.iap.model.Promotion

data class PromotionItem(
  val promotionType: String,
  val promotionPlans: List<PromotionPlanItem>,
) {

  companion object {
    fun from(promotion: Promotion): PromotionItem {
      return PromotionItem(
        promotionType = promotion.promotionType ?: "Null",
        promotionPlans = promotion.promotionPlans.map { promotionPlan ->
          PromotionPlanItem.from(promotionPlan)
        }
      )
    }
  }
}


