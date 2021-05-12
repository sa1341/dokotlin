package com.yolo.dokotlin.board.repository.support

import com.querydsl.jpa.JPQLQuery
import com.yolo.dokotlin.board.dto.BoardSearchType
import com.yolo.dokotlin.board.dto.BoardSearchType.*
import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.entity.QBoard
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BoardSearchService: QuerydslRepositorySupport(Board::class.java) {

    fun search(type: BoardSearchType, value: String, pageable: Pageable): Page<Board> {
        val board: QBoard = QBoard.board
        var query: JPQLQuery<Board>? = null

        when(type) {
            AUTHOR -> query = from(board)
                .where(board.author.likeIgnoreCase("$value%"))
            TITLE -> query = from(board)
                .where(board.title.likeIgnoreCase("$value%"))
            CONTENT -> query = from(board)
                .where(board.content.likeIgnoreCase("$value%"))
            else -> IllegalArgumentException("type is not exist!")
        }

        val boards: List<Board> = querydsl!!.applyPagination(pageable, query!!).fetch()
        return PageImpl(boards, pageable, query!!.fetchCount())
    }
}
