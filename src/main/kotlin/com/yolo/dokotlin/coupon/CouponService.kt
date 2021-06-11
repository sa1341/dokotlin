package com.yolo.dokotlin.coupon

import com.yolo.dokotlin.coupon.repository.CouponRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

@Transactional
@Service
class CouponService {

    @Autowired
    lateinit var couponRuleRepository: CouponRuleRepository

    var ruleMap: ConcurrentHashMap<Long, String> = ConcurrentHashMap()

    //@Scheduled(cron = "* * 3 * * ?")
    @Scheduled(fixedDelay = 3000)
    fun updateRules() {
        val rules = couponRuleRepository.findAll()

        rules.stream().forEach {
            ruleMap.put(it.couponId, it.couponRelation)
        }

        println("ruleMap size: ${ruleMap.size}")
    }
}
