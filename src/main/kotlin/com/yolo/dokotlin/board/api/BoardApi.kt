package com.yolo.dokotlin.board.api

import com.yolo.dokotlin.board.dto.BoardSearchType
import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.board.repository.support.BoardSearchService
import com.yolo.dokotlin.board.service.BoardService
import com.yolo.dokotlin.global.common.model.PageRequest
import com.yolo.dokotlin.reply.repository.ReplySearchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/api/v1/boards")
@RestController
class BoardApi(
    @Autowired private val boardService: BoardService,
    @Autowired private val boardSearchService: BoardSearchService
) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping(path = ["/test"])
    fun test(): BoardDto {
        return BoardDto("임준영", "람다 떡상 가즈아", "람다는 흥할것이다!!!!!")
    }

    @GetMapping(path = ["/{id}"])
    fun getBoard(@PathVariable(value = "id") id: Long): ResponseEntity<BoardDto.Res?> {
        logger.debug("id: [{}]", id)
        val boardRes = boardService.findBoardById(id)
        return ResponseEntity.ok(boardRes)
    }

   @PostMapping
    fun save(@RequestBody @Valid boardDto: BoardDto): ResponseEntity<BoardDto> {
       logger.debug("boardDto: [{}]", boardDto)
       boardService.save(boardDto)
        return ResponseEntity.ok(boardDto)
    }

    @PutMapping(path = ["/{id}"])
    fun update(@PathVariable(value = "id") id:Long, @RequestBody @Valid updateForm: BoardDto.UpdateForm): ResponseEntity<BoardDto.Res?> {
        logger.debug("id: [{}]", id)
        logger.debug("updateForm: [{}]", updateForm)
        val boardRes = boardService.update(id, updateForm)
        return ResponseEntity.ok(boardRes)
    }

    @DeleteMapping(path = ["/{id}"])
    fun delete(@PathVariable(value = "id") id:Long): ResponseEntity<String> {
        logger.debug("id: [{}]", id)

        if (!boardService.delete(id)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }

        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun list(
        @RequestParam(name = "type")  type: BoardSearchType,
        @RequestParam(name = "value", required = false) value: String?,
        pageRequest: PageRequest
    ): Page<BoardDto.Res> {

        logger.debug("type: ${type.name}")
        logger.debug("value: $value")
        logger.debug("pageRequest: ${pageRequest.page}")
        logger.debug("pageRequest: ${pageRequest.size}")

        return boardSearchService.search(type, value, pageRequest.of()).map(Board::toRes)
    }
}
