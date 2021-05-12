package com.yolo.dokotlin.board.service

import com.yolo.dokotlin.board.entity.Reply
import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.board.repository.BoardRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class BoardService(private val boardRepository: BoardRepository) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun save(boardDto: BoardDto) {
        logger.debug("boardDto: ${boardDto.toString()}")
        var board = boardDto.toEntity()
        var reply = Reply.of("배성탑", "테조스 가즈아!!", "조졌다...")
        reply.addBoard(board)
        boardRepository.save(board)
    }

    fun findBoardById(id: Long): BoardDto.Res {
        val board = boardRepository
                    .findById(id)
                    .orElseThrow {IllegalArgumentException("Wrong board id provided")}
        return board.toRes()
    }
}
