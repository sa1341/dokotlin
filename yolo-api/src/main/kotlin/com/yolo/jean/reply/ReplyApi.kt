package com.yolo.jean.reply

import com.yolo.jean.reply.dto.ReplyDto
import com.yolo.jean.reply.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/api/v1/replies")

@RestController
class ReplyApi(
    private val replyService: ReplyService
) {

    @PostMapping("/{id}")
    fun save(@PathVariable("id") id: Long, @RequestBody replyDto: ReplyDto): ResponseEntity<*> {
        replyService.save(id, replyDto)
        return ResponseEntity.ok(HttpStatus.OK)
    }
}
