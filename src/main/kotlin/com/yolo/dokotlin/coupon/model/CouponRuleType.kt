package com.yolo.dokotlin.coupon.model

enum class CouponRuleType(val description: String) {
    APPLICABLE("쿠폰 중복적용 가능한 타입"),
    PRICE("가격 할인정책");
}
