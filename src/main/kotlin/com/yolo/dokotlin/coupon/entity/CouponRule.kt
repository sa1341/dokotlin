package com.yolo.dokotlin.coupon.entity

import com.yolo.dokotlin.coupon.model.CouponRuleType
import javax.persistence.*

@Entity
class CouponRule private constructor(couponId: Long, couponRelation: String , couponRuleType: CouponRuleType) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var couponId: Long = couponId

    var couponRelation: String = couponRelation

    @Enumerated(EnumType.STRING)
    var couponRuleType: CouponRuleType = couponRuleType

    companion object {
        fun of(couponId: Long, couponRelation: String, couponRuleType: CouponRuleType): CouponRule {
            return CouponRule(couponId, couponRelation, couponRuleType)
        }
    }
}
