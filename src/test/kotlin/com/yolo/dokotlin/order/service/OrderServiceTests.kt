package com.yolo.dokotlin.order.service

import com.yolo.dokotlin.coupon.entity.Coupon
import com.yolo.dokotlin.coupon.entity.CouponCode
import com.yolo.dokotlin.coupon.repository.CouponCodeRepository
import com.yolo.dokotlin.coupon.repository.CouponRepository
import com.yolo.dokotlin.item.entity.Book
import com.yolo.dokotlin.item.entity.Computer
import com.yolo.dokotlin.item.repository.ItemRepository
import com.yolo.dokotlin.member.entity.Member
import com.yolo.dokotlin.member.repository.MemberRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class OrderServiceTests {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var couponRepository: CouponRepository

    @Autowired
    lateinit var couponCodeRepository: CouponCodeRepository

    @Autowired
    lateinit var itemRepository: ItemRepository

    @BeforeEach
    fun setUp() {
        var member = Member.of("syn7714@naver.com", "임준영", 30);
        var book = Book.of("전공책", 30000, 10, "임준영", "sa134123132")
        var computer = Computer.of("컴퓨터",  500000, 5, "삼성전자")

        memberRepository.save(member)
        itemRepository.save(book)
        itemRepository.save(computer)

        var coupon = Coupon.of("1000원 할인 쿠폰", 1000.0, LocalDateTime.now())
        var couponCodee = CouponCode.of("W9213-11SD-31SF-15DA", coupon, member)

        couponCodee.addCoupon(coupon)
        couponCodee.addMember(member)

        couponRepository.save(coupon)
        couponCodeRepository.save(couponCodee)
    }

    @Test
    fun 주문에_쿠폰할인을_적용한다() {
        // given

        // when

        // then
    }

}
