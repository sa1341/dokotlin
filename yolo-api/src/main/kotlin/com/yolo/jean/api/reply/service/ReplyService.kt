package com.yolo.jean.api.reply.service

import com.yolo.jean.api.global.error.exception.EntityNotFoundException
import com.yolo.jean.api.global.error.exception.InvalidException
import com.yolo.jean.api.reply.dto.ReplyDto
import com.yolo.jean.domain.board.repository.BoardRepository
import com.yolo.jean.domain.reply.repository.ReplyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReplyService(
    private val boardRepository: BoardRepository,
    private val replyRepository: ReplyRepository
) {

    @Transactional
    fun save(id: Long, replyDto: ReplyDto) {
        val board = boardRepository.findByIdOrNull(id)

        board?.let {
            val reply = replyDto.toEntity()
            reply.addBoard(it)
            reply
        } ?: kotlin.run {
            throw InvalidException("해당 게시글이 삭제 되었습니다. $id")
        }
    }

    @Transactional
    fun delete(id: Long): Boolean {
        val reply = replyRepository.findByIdOrNull(id)

        return reply?.let {
            replyRepository.deleteById(id)
            true
        } ?: kotlin.run {
            false
        }
    }

    @Transactional
    fun update(id: Long, updateForm: ReplyDto.UpdateForm): ReplyDto.Res {
        val reply = replyRepository.findByIdOrNull(id)

        return reply?.let {
            it.updateStatus(updateForm.title, updateForm.content)
            ReplyDto.Res(it.id, it.author, it.title, it.content)
        } ?: kotlin.run {
            throw EntityNotFoundException("해당 댓글이 존재하지 않습니다.: $id")
        }
    }
}
