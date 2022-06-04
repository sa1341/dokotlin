package com.yolo.jean.global.error.exception.constants

enum class ErrorCode(val status: Int, val message: String) {
    INVALID_INPUT_VALUE(400, "Invalid input value"),
    METHOD_NOT_ALLOWED(405, "Method is not allowed"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    ENTITY_NOT_FOUND(400, "Entity not found"),
    METHOD_NOT_VALID(400, "Method is not valid");
}
