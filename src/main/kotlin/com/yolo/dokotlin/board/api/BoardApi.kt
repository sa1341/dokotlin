package com.yolo.dokotlin.board.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/boards")
@RestController
class BoardApi {

    @GetMapping
    fun test(): String {
        return "코틀린 세계에 오신걸 환영합니다."
    }
}
