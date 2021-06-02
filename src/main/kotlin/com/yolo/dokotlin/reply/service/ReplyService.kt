package com.yolo.dokotlin.reply.service

import com.yolo.dokotlin.board.repository.BoardRepository
import com.yolo.dokotlin.global.error.exception.EntityNotFoundException
import com.yolo.dokotlin.reply.dto.ReplyDto
import com.yolo.dokotlin.reply.entity.Reply
import com.yolo.dokotlin.reply.repository.ReplyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ReplyService(
    @Autowired private val boardRepository: BoardRepository,
    @Autowired private val replyRepository: ReplyRepository
) {

    @Transactional
    fun save(id: Long, replyDto: ReplyDto): Reply {
        val board = boardRepository.findByIdOrNull(id)

        return board?.let {
            val reply = replyDto.toEntity()
            reply.addBoard(it)
            reply
        } ?: kotlin.run {
            throw EntityNotFoundException("Board Id is not exist: $id")
        }
    }

    @Transactional
    fun delete(id: Long): Boolean {
        val reply = replyRepository.findById(id)

        return reply?.let {
            replyRepository.deleteById(id)
            true
        }?: kotlin.run {
            false
        }
    }

    @Transactional
    fun update(id: Long, updateForm: ReplyDto.UpdateForm): ReplyDto.Res {
        val reply = replyRepository.findByIdOrNull(id)

       return reply?.let {
            it.updateStatus(updateForm.title, updateForm.content)
            it.toRes()
        }?:kotlin.run {
            throw EntityNotFoundException("reply id is not exist: $id")
        }
    }
}
