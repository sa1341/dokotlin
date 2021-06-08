package com.yolo.dokotlin.coupon.entity

import com.yolo.dokotlin.coupon.model.CouponStatus
import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import com.yolo.dokotlin.member.entity.Member
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;

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

    var couponStatus = CouponStatus.UN_USE

    companion object {
        fun of(code: String, coupon: Coupon, member: Member): CouponCode {
            return CouponCode(code, coupon, member)
        }
    }

    fun addCoupon(coupon: Coupon) {
        this.coupon = coupon
    }

    fun addMember(member: Member) {
        this.member = member
        member.couponCodes.add(this)
    }
}

