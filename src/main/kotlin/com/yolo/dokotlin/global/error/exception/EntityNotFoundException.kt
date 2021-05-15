package com.yolo.dokotlin.global.error.exception

class EntityNotFoundException(message: String): BusinessException(message, ErrorCode.ENTITY_NOT_FOUND)

