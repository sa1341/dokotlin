package com.yolo.dokotlin.reply.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.board.repository.BoardRepository
import com.yolo.dokotlin.global.error.EntityNotFoundException
import com.yolo.dokotlin.reply.entity.QReply.*
import com.yolo.dokotlin.reply.entity.Reply
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReplySearchService(
    @Autowired private val queryFactory: JPAQueryFactory,
    @Autowired private val boardRepository: BoardRepository
) {

    @Transactional(readOnly = true)
    fun getBoardWithReplies(id: Long): BoardDto.Res? {
        val board = boardRepository.findByIdOrNull(id)

        return board?.let {
            val replies = findRepliesWithBoard(it)
            val repliesDto = replies.map(Reply::toDto).toMutableList()
            val boardRes = it.toRes()
            boardRes.addReplies(repliesDto)
            boardRes
        } ?: kotlin.run {
            throw EntityNotFoundException("Board Id is not exist: $id")
        }
    }

    fun findRepliesWithBoard(board: Board?): MutableList<Reply> {
        return queryFactory.selectFrom(reply)
            .where(reply.board.eq(board))
            .limit(2)
            .orderBy(reply.id.desc())
            .fetch()
    }
}
