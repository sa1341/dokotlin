package com.yolo.jean.api.global.error.exception

import com.yolo.jean.api.global.error.exception.constants.ErrorCode

class EntityNotFoundException(message: String) : BusinessException(message, ErrorCode.ENTITY_NOT_FOUND)
