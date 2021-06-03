package com.yolo.dokotlin.coupon.repository

import com.yolo.dokotlin.coupon.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository: JpaRepository<Coupon, Long>
