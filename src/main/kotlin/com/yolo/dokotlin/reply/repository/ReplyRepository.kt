package com.yolo.dokotlin.reply.repository

import com.yolo.dokotlin.reply.entity.Reply
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository: JpaRepository<Reply, Long>
