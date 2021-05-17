package com.yolo.dokotlin.reply.service

import com.yolo.dokotlin.board.repository.BoardRepository
import com.yolo.dokotlin.global.error.EntityNotFoundException
import com.yolo.dokotlin.reply.dto.ReplyDto
import com.yolo.dokotlin.reply.entity.Reply
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ReplyService(private val boardRepository: BoardRepository) {

    @Transactional
    fun save(id: Long, replyDto: ReplyDto): Reply {
        val board = boardRepository.findByIdOrNull(id)

        return board?.let {
            val reply = replyDto.toEntity()
            reply.addBoard(it)
            reply
        }?:kotlin.run {
            throw EntityNotFoundException("Board Id is not exist: $id")
        }
    }
}
