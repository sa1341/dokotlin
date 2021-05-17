package com.yolo.dokotlin.board.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.entity.QBoard.board
import com.yolo.dokotlin.board.entity.QReply.reply
import com.yolo.dokotlin.board.entity.Reply
import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.global.error.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReplySearchService(private val queryFactory: JPAQueryFactory) {

    @Transactional(readOnly = true)
    fun getBoardWithReplies(id: Long?): BoardDto.Res? {
        val board = findBoardWithReplies(id)

        return board?.let {
            var replies = board.replies.map(Reply::toRes).toMutableList()
            val boardRes = it.toRes()
            boardRes.addReplies(replies)
            boardRes
        } ?: kotlin.run {
            throw EntityNotFoundException("Board Id is not exist: $id")
        }
    }

    fun findBoardWithReplies(id: Long?): Board? {
        return queryFactory.selectFrom(board)
            .leftJoin(board.replies, reply)
            .where(board.id.eq(id))
            .fetchJoin()
            .fetchOne()
    }
}
