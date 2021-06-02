package com.yolo.dokotlin.member.repository

import com.yolo.dokotlin.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>
