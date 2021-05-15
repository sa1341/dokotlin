package com.yolo.dokotlin.global.error.exception

open class BusinessException(override val message: String): RuntimeException(message) {

    open lateinit var errorCode: ErrorCode

    constructor(message: String, errorCode: ErrorCode) : this(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode) : this(errorCode.message) {
        this.errorCode = errorCode
    }
}
