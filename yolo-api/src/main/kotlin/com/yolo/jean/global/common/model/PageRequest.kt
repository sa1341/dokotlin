package com.yolo.jean.global.common.model

import com.yolo.jean.domain.common.logger
import org.springframework.data.domain.Sort
import org.springframework.data.domain.PageRequest


class PageRequest {

    private val log by logger()

    var page: Int = 1
        set(value) {
            field = if (value <= 0) 1 else value
        }

    var size: Int = 10
        set(value) {
            log.debug("value: $value")
            field = if (value > DEFAULT_MAX_SIZE) DEFAULT_SIZE else value
        }

    var direction: Sort.Direction = Sort.Direction.ASC

    companion object {
        const val DEFAULT_SIZE = 10
        const val DEFAULT_MAX_SIZE = 50
    }

    fun of(): PageRequest {
        return PageRequest.of(page - 1, size, direction, "createdAt")
    }
}
