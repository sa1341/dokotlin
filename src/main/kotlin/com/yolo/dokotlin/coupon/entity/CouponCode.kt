package com.yolo.dokotlin.coupon.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import com.yolo.dokotlin.member.entity.Member
import javax.persistence.*

@Entity
class CouponCode private constructor(_code: String, _coupon: Coupon, _member: Member): BaseTimeEntity() {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null

    var code: String = _code

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    var coupon: Coupon = _coupon

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member = _member

    companion object {
        fun of(code: String, coupon: Coupon): CouponCode {
            return CouponCode(code, coupon)
        }
    }
}
