package com.yolo.dokotlin.global.error

import com.yolo.dokotlin.global.error.exception.BusinessException
import com.yolo.dokotlin.global.error.exception.ErrorCode

class EntityNotFoundException(message: String?) : BusinessException(message!!, ErrorCode.ENTITY_NOT_FOUND)
