package com.yolo.dokotlin.coupon.repository

import com.yolo.dokotlin.coupon.entity.CouponRule
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRuleRepository: JpaRepository<CouponRule, Long>
