package com.yolo.dokotlin

import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.repository.BoardRepository
import com.yolo.dokotlin.coupon.entity.Coupon
import com.yolo.dokotlin.coupon.entity.CouponCode
import com.yolo.dokotlin.coupon.repository.CouponRepository
import com.yolo.dokotlin.member.entity.Member
import com.yolo.dokotlin.member.repository.MemberRepository
import com.yolo.dokotlin.reply.entity.Reply
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
class DokotlinApplication(var boardRepository: BoardRepository): CommandLineRunner {

	@Autowired
	lateinit var memberRepository: MemberRepository

	@Autowired
	lateinit var couponRepository: CouponRepository

	@Transactional
	override fun run(vararg args: String?) {
		println("애플리케이션 실행한다!")
	/*	var member = Member.of("a79007714@gmail.com", "임준영", 30)

		var coupon1 = Coupon.of("나라사랑쿠폰", 1000.0, LocalDateTime.of(2021, 12, 31, 23, 59, 59))
		var coupon2 = Coupon.of("베타쿠폰", 3000.0, LocalDateTime.of(2021, 12, 31, 23, 59, 59))
		var coupon3 = Coupon.of("달채비쿠폰", 2000.0, LocalDateTime.of(2021, 12, 31, 23, 59, 59))

		var couponCode1 = CouponCode.of("SJDI123S", coupon1, member)
		var couponCode2 = CouponCode.of("FJKLAJU1", coupon2, member)
		var couponCode3 = CouponCode.of("ZXLJ2421", coupon3, member)

		coupon1.couponCode.add(couponCode1)
		coupon2.couponCode.add(couponCode2)
		coupon3.couponCode.add(couponCode3)

		member.couponCodes.add(couponCode1)
		member.couponCodes.add(couponCode2)
		member.couponCodes.add(couponCode3)

		couponRepository.save(coupon1)
		couponRepository.save(coupon2)
		couponRepository.save(coupon3)

		memberRepository.save(member)*/
	}
}

fun main(args: Array<String>) {
	runApplication<DokotlinApplication>(*args)
}

