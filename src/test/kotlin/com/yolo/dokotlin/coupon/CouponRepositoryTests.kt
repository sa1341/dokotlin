package com.yolo.dokotlin.coupon

import com.yolo.dokotlin.coupon.entity.Coupon
import com.yolo.dokotlin.coupon.entity.CouponCode
import com.yolo.dokotlin.coupon.repository.CouponCodeRepository
import com.yolo.dokotlin.coupon.repository.CouponRepository
import com.yolo.dokotlin.member.entity.Member
import com.yolo.dokotlin.member.repository.MemberRepository
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
import java.time.LocalDateTime

@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class CouponRepositoryTests {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var couponRepository: CouponRepository

    @Autowired
    lateinit var couponCodeRepository: CouponCodeRepository


    @BeforeEach
    fun setUp() {

        var member = Member.of("syn7714@naver.com", "임준영", 30);
        var coupon = Coupon.of("10% 할인", LocalDateTime.now())
        var couponCodee = CouponCode.of("W9213-11SD-31SF-15DA", coupon, member)

        couponCodee.addCoupon(coupon)
        couponCodee.addMember(member)

        memberRepository.save(member)
        couponRepository.save(coupon)
        couponCodeRepository.save(couponCodee)

    }

    @Test
    fun 쿠폰을_생성한다() {

        // given
        val couponCode = couponCodeRepository.findByIdOrNull(1L);

        // when
        couponCode?.let {
            println("id: ${couponCode.id}")
            println("name: ${couponCode.code}")
            println("name: ${couponCode.coupon.name}")
            println("name: ${couponCode.member.email}")
        }?:kotlin.run {
            println("Coupon is not exist")
        }

        // then
        assertThat(couponCode?.code).isEqualTo("W9213-11SD-31SF-15DA");
    }
}
