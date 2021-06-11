package com.yolo.dokotlin.coupon.service

import com.yolo.dokotlin.coupon.CouponService
import com.yolo.dokotlin.coupon.repository.CouponCodeRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@ExtendWith(SpringExtension::class)
@SpringBootTest
class CouponServiceTests {

    @Autowired
    lateinit var couponService: CouponService

    @Autowired
    lateinit var couponCodeRepository: CouponCodeRepository

    @BeforeEach
    fun setUp() {
        couponService.updateRules()
    }

    @Transactional
    @Test
    fun 쿠폰_룰을_적용한다() {

        // given
        val couponCode1 = couponCodeRepository.findByIdOrNull(1L)
        val couponCode2 = couponCodeRepository.findByIdOrNull(2L)
        val couponCode3 = couponCodeRepository.findByIdOrNull(2L)
        var queue: Queue<Long> = LinkedList()

       queue.offer(couponCode1!!.coupon.id)
       queue.offer(couponCode2!!.coupon.id)
       queue.offer(couponCode3!!.coupon.id)

        var ruleMap = couponService.ruleMap

        // when
        var result = validateApplicableCoupon(queue, ruleMap)
        println("result: $result")

        // then
        assertThat(result).isEqualTo(false)
    }

    private fun validateApplicableCoupon (
        queue: Queue<Long>,
        ruleMap: ConcurrentHashMap<Long, String>
    ): Boolean {

        var count = 0
        var isValid : Boolean = true

        while (count != queue.size) {

            var temp = queue.remove()
            val relation = ruleMap.get(temp)!!.split(",").toList()
            println("temp: $temp, relation: $relation")

            isValid = checkDupApplicable(queue, relation)
            if (!isValid) break;
            queue.offer(temp)
            count++
        }
        return isValid
    }

    // TODO: 2021/06/11   큐에 있는 쿠폰과 쿠폰 릴레이션과 비교하여 검증하는 로직 작성하기
    private fun checkDupApplicable(queue: Queue<Long>, relation: List<String>): Boolean {
        var result = false

        queue.forEach {
            var couponId = it
            result  = relation.stream().anyMatch {
                it.toLongOrNull() == couponId
            }
            if (!result) return false
        }

        return result
    }
}
