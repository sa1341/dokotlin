package com.yolo.dokotlin.board.api

import com.yolo.dokotlin.global.entity.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/boards")
@RestController
class BoardApi {

    @GetMapping("/test")
    fun test() = "코틀린 세계에 오신걸 환영합니다."

    @GetMapping
    fun list(pageRequest: PageRequest) {
        println("pageRequest: ${pageRequest.page}")
        println("pageRequest: ${pageRequest.size}")
    }
}
