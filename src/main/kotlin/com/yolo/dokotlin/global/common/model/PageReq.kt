package com.yolo.dokotlin.global.common.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class PageReq {
    private var page = 0
    private var size = 0
    var direction: Sort.Direction? = null
    fun setPage(page: Int) {
        this.page = if (page <= 0) 1 else page
    }

    fun setSize(size: Int) {
        val DEFAULT_SIZE = 10
        val MAX_SIZE = 50
        this.size = if (size > MAX_SIZE) DEFAULT_SIZE else size
    }

    fun getPage(): Int {
        return page
    }

    fun getSize(): Int {
        return size
    }

    fun of(): PageRequest {
        return PageRequest.of(page - 1, size, direction!!, "createdAt")
    }
}
