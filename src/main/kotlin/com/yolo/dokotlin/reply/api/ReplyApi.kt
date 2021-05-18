package com.yolo.dokotlin.reply.api

import com.yolo.dokotlin.global.common.model.PageRequest
import com.yolo.dokotlin.reply.dto.ReplyDto
import com.yolo.dokotlin.reply.entity.Reply
import com.yolo.dokotlin.reply.repository.support.ReplySearchService
import com.yolo.dokotlin.reply.service.ReplyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/api/v1/boards")
@RestController
class ReplyApi(
    private val replySearchService: ReplySearchService,
    private val replyService: ReplyService
) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping(path = ["/{id}/replies"])
    fun save(@PathVariable(name = "id") id: Long, @RequestBody @Valid replyDto: ReplyDto): ResponseEntity<ReplyDto.Res> {
        logger.debug("id: {}, replyDto: {}", id, replyDto)
        val reply = replyService.save(id, replyDto)
        return ResponseEntity.ok(reply.toRes())
    }

    @PutMapping(path = ["/{id}/replies"])
    fun update(@PathVariable(name = "id") id: Long, @RequestBody @Valid updateForm: ReplyDto.UpdateForm): ReplyDto.Res {
        logger.debug("id: {}", id)
        logger.debug("updateForm: {}", updateForm)
        return replyService.update(id, updateForm)
    }

    @GetMapping(path = ["/{id}/replies"])
    fun getReplies(@PathVariable(value = "id") id: Long,  pageRequest: PageRequest): Page<ReplyDto.Res> {
        logger.debug("id: {}", id)
        logger.debug("pageRequest: {}", pageRequest)
        return replySearchService.getReplies(id, pageRequest.of()).map(Reply::toRes)
    }

    @DeleteMapping(path = ["/{id}/replies"])
    fun delete(@PathVariable(value = "id") id: Long, pageRequest: PageRequest): ResponseEntity<String> {
        logger.debug("id: {}", id)
        logger.debug("pageRequest: {}", pageRequest)

        if (!replyService.delete(id)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
        return ResponseEntity.ok().build()
    }
}
