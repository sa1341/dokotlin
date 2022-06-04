package com.yolo.jean.board.service

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.JPQLQuery
import com.yolo.jean.domain.board.constants.BoardSearchType
import com.yolo.jean.domain.board.entity.Board
import com.yolo.jean.domain.board.entity.QBoard
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.util.*

@Service
class BoardSearchService: QuerydslRepositorySupport(Board::class.java) {

    @Transactional(readOnly = true)
    fun search(type: BoardSearchType, value: String?, pageable: Pageable): Page<Board> {
        val board: QBoard = QBoard.board
        var query: JPQLQuery<Board> = from(board).where(searchBoardByCond(board, type, value))
        val boards: List<Board> = querydsl?.applyPagination(pageable, query)!!.fetch()
        return PageImpl(boards, pageable, query!!.fetchCount())
    }

    private fun searchBoardByCond(board: QBoard, searchType: BoardSearchType, value: String?): BooleanBuilder {
        val builder = BooleanBuilder()

        when (searchType) {
            BoardSearchType.AUTHOR -> if (StringUtils.hasText(value)) builder.and(board.author.likeIgnoreCase("$value%"))
            BoardSearchType.TITLE -> if (StringUtils.hasText(value)) builder.and(board.title.likeIgnoreCase("%$value%"))
            BoardSearchType.CONTENT -> if (StringUtils.hasText(value)) builder.and(board.content.likeIgnoreCase("%$value%"))
            else -> InvalidPropertiesFormatException("Type is not exist: ${searchType.name}")
        }

        return builder
    }
}


