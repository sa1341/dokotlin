package com.yolo.dokotlin.coupon

import com.yolo.dokotlin.coupon.repository.CouponRuleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Transactional
@Service
class CouponService {

    lateinit var couponRuleRepository: CouponRuleRepository

    var ruleMap: Map<Long, String> = hashMapOf()

    @PostConstruct
    fun setUp() {
        val rules = couponRuleRepository.findAll()
        ruleMap = rules.map { it.couponId to it.couponRelation }.toMap()
    }
}
