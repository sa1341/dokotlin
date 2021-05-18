package com.yolo.dokotlin.reply.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.yolo.dokotlin.board.entity.QBoard
import com.yolo.dokotlin.board.repository.BoardRepository
import com.yolo.dokotlin.reply.entity.QReply
import com.yolo.dokotlin.reply.entity.Reply
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReplySearchService(
    @Autowired private val queryFactory: JPAQueryFactory,
    @Autowired private val boardRepository: BoardRepository
): QuerydslRepositorySupport(Reply::class.java){

    @Transactional(readOnly = true)
    fun getReplies(id: Long, pageable: Pageable): Page<Reply> {
        val findBoard = from(QBoard.board).where(QBoard.board.id.eq(id)).fetchOne()
        val reply: QReply = QReply.reply
        // TODO: 2021/05/18 이부분 수정필요함.
        var query = from(reply).where(reply.board.eq(findBoard))
        val replies: List<Reply> = querydsl!!.applyPagination(pageable, query!!).fetch()
        return PageImpl(replies, pageable, query!!.fetchCount())
    }
}
