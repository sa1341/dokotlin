package com.yolo.dokotlin.global.entity

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class PageRequest private constructor(_page: Int = 1, _size: Int = 10, _direction: Sort.Direction = Sort.Direction.ASC){
    var page: Int = _page
        set(value) {
            field = if (value <= 0) 1 else value
        }

    var size: Int = _size
        set(value) {
            println("value: $value")
            field = if (value > DEFAULT_MAX_SIZE) DEFAULT_SIZE else value
         }

    var direction: Sort.Direction = _direction

    companion object {
        const val DEFAULT_SIZE = 10
        const val DEFAULT_MAX_SIZE = 50
    }

    fun of(): PageRequest {
        return PageRequest.of(page - 1, size, direction)
    }
}
