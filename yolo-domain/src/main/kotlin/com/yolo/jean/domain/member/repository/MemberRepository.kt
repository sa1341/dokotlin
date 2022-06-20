package com.yolo.jean.domain.member.repository

import com.yolo.jean.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findMemberByEmail(email: String): Member?
}
