package com.yolo.dokotlin.member.entity

import com.yolo.dokotlin.coupon.entity.CouponCode
import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import javax.persistence.*

@Entity
class Member private constructor(_email: String, _name: String, _age: Number): BaseTimeEntity() {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Id
    val id: Long? = null
    var email: String = _email
    var name: String = _name
    var age: Number = _age

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var couponCodes: MutableList<CouponCode> = mutableListOf()

    companion object {
        fun of(_email: String, _name: String, _age: Number): Member {
            return Member(_email, _name, _age)
        }
    }
}
