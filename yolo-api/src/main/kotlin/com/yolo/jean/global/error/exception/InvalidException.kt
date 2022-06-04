package com.yolo.jean.global.error.exception

import com.yolo.jean.global.error.exception.constants.ErrorCode

class InvalidException(message: String): BusinessException(message, ErrorCode.INVALID_INPUT_VALUE)

