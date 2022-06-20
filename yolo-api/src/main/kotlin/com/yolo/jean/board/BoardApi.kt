package com.yolo.jean.board

import com.yolo.jean.board.dto.BoardDto
import com.yolo.jean.board.dto.Result
import com.yolo.jean.board.service.BoardSearchService
import com.yolo.jean.board.service.BoardService
import com.yolo.jean.domain.board.constants.BoardSearchType
import com.yolo.jean.domain.board.entity.Board
import com.yolo.jean.global.common.model.PageRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
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

    private val log = LoggerFactory.getLogger(this.javaClass)


    @Operation(summary = "getBoard TEST", description = "getBoard TEST")
    @ApiResponses(
        ApiResponse(description = "OK", responseCode = "200"),
        ApiResponse(description = "BAD REQUEST", responseCode = "400"),
        ApiResponse(description = "NOT FOUND", responseCode = "404"),
        ApiResponse(description = "INTERNAL SERVER ERROR", responseCode = "500")
    )
    @GetMapping(path = ["/{id}"])
    fun getBoard(@Parameter(description = "게시글 번호", required = true, example = "1")
                 @PathVariable(value = "id") id: Long): ResponseEntity<BoardDto.Res> {
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

    @GetMapping("member/{email}")
    fun getBoardWithReplies(@PathVariable("email") email: String): ResponseEntity<Result<*>> {
        val result = boardService.getBoardsWithReplies(email)
        return ResponseEntity.ok().body(result)
    }
}
