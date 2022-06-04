package com.yolo.jean.domain.reply.repository

import com.yolo.jean.domain.reply.entity.Reply
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository: JpaRepository<Reply, Long> {
}
