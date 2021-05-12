package com.yolo.dokotlin.global.common.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class PageRequest {
    var page: Int = 1
        set(value) {
            field = if (value <= 0) 1 else value
        }

    var size: Int = 10
        set(value) {
            println("value: $value")
            field = if (value > DEFAULT_MAX_SIZE) DEFAULT_SIZE else value
         }

    var direction: Sort.Direction = Sort.Direction.ASC

    companion object {
        const val DEFAULT_SIZE = 10
        const val DEFAULT_MAX_SIZE = 50
    }

    fun of(): PageRequest {
        return PageRequest.of(page - 1, size, direction, "createdDate")
    }
}
