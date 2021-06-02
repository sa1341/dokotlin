package com.yolo.dokotlin.member.repository

import com.yolo.dokotlin.member.entity.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@Transactional
@ExtendWith(SpringExtension::class)
@ActiveProfiles(value = ["dev"])
@SpringBootTest
class MemberRepositoryTests {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun test() {
        // given
        var member = Member.of("a79007714@gmail.com", "임준영", 30)

        // when
        memberRepository.save(member)

        println("id: ${member.id}")

        // then
        assertThat(member.name).isEqualTo("임준영")
    }
}
