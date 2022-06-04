package com.yolo.jean.board

import com.yolo.jean.board.dto.BoardDto
import com.yolo.jean.board.service.BoardSearchService
import com.yolo.jean.board.service.BoardService
import com.yolo.jean.domain.board.constants.BoardSearchType
import com.yolo.jean.domain.board.entity.Board
import com.yolo.jean.domain.common.logger
import com.yolo.jean.global.common.model.PageRequest
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping(path = ["/api/v1/boards"])
@RestController
class BoardApi(
    private val boardService: BoardService,
    private val boardSearchService: BoardSearchService
) {

    private val log by logger()

    @GetMapping(path = ["/{id}"])
    fun getBoard(@PathVariable(value = "id") id: Long): ResponseEntity<BoardDto.Res> {
        log.info("id: [{}]", id)
        val boardRes = boardService.findBoardById(id)
        return ResponseEntity.ok(boardRes)
    }

    @PostMapping
    fun save(@RequestBody @Valid boardDto: BoardDto): ResponseEntity<BoardDto> {
        log.info("boardDto: [{}]", boardDto)
        boardService.save(boardDto)
        return ResponseEntity.ok(boardDto)
    }

    @GetMapping
    fun list(
        @RequestParam(name = "type")  type: BoardSearchType,
        @RequestParam(name = "value", required = false) value: String?,
        pageRequest: PageRequest
    ): Page<Board> {

        log.debug("type: ${type.name}")
        log.debug("value: $value")
        log.debug("pageRequest: ${pageRequest.page}")
        log.debug("pageRequest: ${pageRequest.size}")

        return boardSearchService.search(type, value, pageRequest.of())
    }
}
