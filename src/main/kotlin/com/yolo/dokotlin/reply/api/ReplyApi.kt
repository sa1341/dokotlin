package com.yolo.dokotlin.reply.api

import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.reply.dto.ReplyDto
import com.yolo.dokotlin.reply.repository.ReplyRepository
import com.yolo.dokotlin.reply.repository.ReplySearchService
import com.yolo.dokotlin.reply.service.ReplyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/api/v1/boards")
@RestController
class ReplyApi(
    private val replySearchService: ReplySearchService,
    private val replyService: ReplyService,
) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping(path = ["/{id}/replies"])
    fun save(@PathVariable(name = "id") id: Long, @RequestBody @Valid replyDto: ReplyDto): ResponseEntity<ReplyDto.Res> {
        logger.debug("id: {}, replyDto: {}", id, replyDto)
        val reply = replyService.save(id, replyDto)
        return ResponseEntity.ok(reply.toRes())
    }

    @GetMapping(path = ["/{id}/replies"])
    fun getReplies(@PathVariable(value = "id") id: Long): ResponseEntity<BoardDto.Res?> {
        logger.debug("id: {}", id)
        val boardRes = replySearchService.getBoardWithReplies(id)
        return ResponseEntity.ok(boardRes)
    }
}
