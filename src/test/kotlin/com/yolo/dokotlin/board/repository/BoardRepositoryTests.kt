package com.yolo.dokotlin.board.repository

import com.yolo.dokotlin.board.entity.Board
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
    private lateinit var boardRepository: BoardRepository

    @Test
    fun 게시판을_저장한다() {

        // given
        val board = Board()
        board.author = "임준영"
        board.title = "do it 코틀린"
        board.content = "코틀린 공부 2일차"

        // when
        boardRepository.save(board)
        val optBoard = board.id?.let { boardRepository.findById(it) }
        val findBoard = optBoard?.get()

        //then
        assertThat(findBoard?.author).isEqualTo("임준영")
    }
}
