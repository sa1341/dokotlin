package com.yolo.dokotlin.global.error.exception

class InvalidException(message: String): BusinessException(message, ErrorCode.INVALID_INPUT_VALUE)
