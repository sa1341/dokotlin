package com.yolo.dokotlin.coupon.repository

import com.yolo.dokotlin.coupon.entity.CouponCode
import org.springframework.data.jpa.repository.JpaRepository

interface CouponCodeRepository: JpaRepository<CouponCode, Long>
