package com.yolo.dokotlin.board.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.entity.QBoard.board
import com.yolo.dokotlin.reply.entity.Reply
import com.yolo.dokotlin.reply.repository.ReplySearchService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class BoardRepositoryTests {

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var replySearchService: ReplySearchService

    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun setUp() {
        val board = Board.of("임준영", "나는 왜 살고 있는가?", "인간이기 때문이다")
        var reply = Reply.of("배성탑", "테조스 가즈아!!", "조졌다...")
        reply.addBoard(board)

        // when
        boardRepository.save(board)
    }

    @Test
    fun 게시판을_저장한다() {
        // given
        val board = Board.of("임준영", "do it 코틀린", "코틀린 공부 2일차")

        // when
        boardRepository.save(board)
        val id = board.id

        // then
        if (id != null) {
            val optional = boardRepository.findById(id)
            val findBoard = optional.get();
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

    @Test
    fun lazy_loading을_테스트한다() {
        // when
        var findBoard = boardRepository.findByIdOrNull(1L)
        println("board: ${findBoard?.author}")
        println("board: ${findBoard?.title}")
        println("board: ${findBoard?.content}")
    //assertThat(findBoard.replies).extracting("author").contains("배성탑")
    }

    @Test
    fun 게시글_및_댓글을_함께_조회한다() {

        // given
        var board = Board.of("임준영", "람다 떡상 가즈아", "하하하 어딜가야하오...")
        var reply1 = Reply.of("배성탑", "도지코인 떡상 가즈아", "머스크형 날구해줘")
        var reply2 = Reply.of("오영선", "목대 동창회 모집 고고씽", "회장은 오영선, 총무는 이재동입니다.")

        reply1.addBoard(board)
        reply2.addBoard(board)
        boardRepository.save(board)

        // when
        val result = board.id?.let { replySearchService.getBoardWithReplies(it) }

        // then
        assertThat(result?.author).isEqualTo("임준영")
        assertThat(result?.replies).extracting("title").contains("도지코인 떡상 가즈아")
    }
}
