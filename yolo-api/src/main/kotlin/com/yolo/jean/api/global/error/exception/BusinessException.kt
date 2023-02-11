package com.yolo.jean.api.global.error.exception

import com.yolo.jean.api.global.error.exception.constants.ErrorCode

open class BusinessException(override val message: String): RuntimeException(message) {

    lateinit var errorCode: ErrorCode

    constructor(message: String, errorCode: ErrorCode) : this(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode) : this(errorCode.message) {
        this.errorCode = errorCode
    }
}
