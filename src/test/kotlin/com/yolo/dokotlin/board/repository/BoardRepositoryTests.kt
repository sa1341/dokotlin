package com.yolo.dokotlin.board.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.entity.QBoard
import com.yolo.dokotlin.board.entity.QBoard.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class BoardRepositoryTests {

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Test
    fun 게시판을_저장한다() {

        // given
        val board = Board.of("임준영", "do it 코틀린", "코틀린 공부 2일차")

        // when
        boardRepository.save(board)
        val id = board.id

        if (id != null) {
            val optional = boardRepository.findById(id)
            val findBoard = optional.get();
            // then
            assertThat(board.id!!).isEqualTo(findBoard.id)
        };
    }

    @Test
    fun 게시판을_조회한다() {
        val _board = Board.of("임준영", "do it 코틀린", "코틀린 공부 2일차")

        // when
        boardRepository.save(_board)

        val findBoard = queryFactory.selectFrom(board)
            .where(searchUser(_board.id!!))
            .fetchOne()

        assertThat(findBoard?.author).isEqualTo("임준영")
    }

    fun searchUser(id: Long): BooleanBuilder {
        val builder = BooleanBuilder()

        if (id != null) {
            builder.and(board.id.eq(id))
        }
        return builder
    }
}
