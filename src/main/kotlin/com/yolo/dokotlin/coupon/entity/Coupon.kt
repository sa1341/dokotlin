package com.yolo.dokotlin.coupon.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Coupon private constructor(_name: String, _expiredAt: LocalDateTime): BaseTimeEntity() {

    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null

    var name: String = _name

    var isAvailable: Boolean = true

    var expiredAt: LocalDateTime = _expiredAt

    companion object {
        fun of(name: String, expiredAt: LocalDateTime): Coupon {
            return Coupon(name, expiredAt)
        }
    }

}
