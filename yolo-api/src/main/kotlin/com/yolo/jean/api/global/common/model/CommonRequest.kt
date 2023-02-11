package com.yolo.jean.api.global.common.model

data class CommonRequest<out T>(
    val commonHeaders: CommonHeaders,
    val data: T
) {

}

