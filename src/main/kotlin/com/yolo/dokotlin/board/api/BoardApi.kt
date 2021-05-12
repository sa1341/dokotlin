package com.yolo.dokotlin.board.api

import com.yolo.dokotlin.board.dto.BoardSearchType
import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.board.repository.support.BoardSearchService
import com.yolo.dokotlin.board.service.BoardService
import com.yolo.dokotlin.global.common.model.PageRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/boards")
@RestController
class BoardApi(@Autowired private val boardService: BoardService, @Autowired private val boardSearchService: BoardSearchService) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/test")
    fun test() = "코틀린 세계에 오신걸 환영합니다."

    @GetMapping("/{id}")
    fun getBoard(@PathVariable(value = "id") id: Long): BoardDto.Res {
        val boardRes = boardService.findBoardById(id)
        return boardRes
    }

    @PostMapping
    fun save(@RequestBody boardDto: BoardDto) {
        boardService.save(boardDto)
    }

    @GetMapping
    fun list(@RequestParam(name = "type") type: BoardSearchType, @RequestParam(name = "value", required = false) value: String, pageRequest: PageRequest): Page<BoardDto.Res> {
        logger.debug("type: ${type.name}")
        logger.debug("value: $value")
        logger.debug("pageRequest: ${pageRequest.page}")
        logger.debug("pageRequest: ${pageRequest.size}")
        logger.debug("pageRequest: ${type.name}")

        val boards = boardSearchService.search(type, value, pageRequest.of()).map(Board::toRes)
        return boards
    }
}
