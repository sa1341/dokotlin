package com.yolo.dokotlin.board.repository.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.JPQLQuery
import com.yolo.dokotlin.board.dto.BoardSearchType
import com.yolo.dokotlin.board.dto.BoardSearchType.*
import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.entity.QBoard
import com.yolo.dokotlin.global.error.exception.InvalidException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils.hasText

@Service
@Transactional(readOnly = true)
class BoardSearchService: QuerydslRepositorySupport(Board::class.java) {

    fun search(type: BoardSearchType, value: String?, pageable: Pageable): Page<Board> {
        val board: QBoard = QBoard.board
        var query: JPQLQuery<Board>? = null
        query = from(board).where(searchBoardByCond(board, type, value))
        val boards: List<Board> = querydsl!!.applyPagination(pageable, query!!).fetch()
        return PageImpl(boards, pageable, query!!.fetchCount())
    }

    fun searchBoardByCond(board: QBoard, searchType: BoardSearchType, value: String?): BooleanBuilder {
        val builder = BooleanBuilder()

        when (searchType) {
            AUTHOR -> if (hasText(value)) builder.and(board.author.likeIgnoreCase("$value%"))
            TITLE -> if (hasText(value)) builder.and(board.title.likeIgnoreCase("%$value%"))
            CONTENT -> if (hasText(value)) builder.and(board.content.likeIgnoreCase("%$value%"))
            else -> InvalidException("Type is not exist: ${searchType.name}")
        }

        return builder
    }
}
