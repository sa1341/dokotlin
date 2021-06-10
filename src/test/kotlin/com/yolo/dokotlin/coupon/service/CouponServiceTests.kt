package com.yolo.dokotlin.coupon.service

import com.yolo.dokotlin.coupon.CouponService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class CouponServiceTests {

    @Autowired
    lateinit var couponService: CouponService

    @Test
    fun 쿠폰_룰을_적용한다() {

        // given
        

        // when

        // then
    }

}
