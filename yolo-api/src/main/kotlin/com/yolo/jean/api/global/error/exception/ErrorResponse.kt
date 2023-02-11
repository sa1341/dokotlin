package com.yolo.jean.api.global.error.exception

import com.yolo.jean.api.global.error.exception.constants.ErrorCode
import org.springframework.validation.BindingResult
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

class ErrorResponse {

    var message: String? = null
    var status: Int? = null
    var errors: MutableList<FieldError>? = null

    private constructor(errorCode: ErrorCode, errors: MutableList<FieldError>) {
        this.message = errorCode.message
        this.status = errorCode.status
        this.errors = errors
    }

    private constructor(errorCode: ErrorCode, message: String) {
        this.message = message
        this.status = errorCode.status
        this.errors = mutableListOf()
    }

    private constructor(errorCode: ErrorCode) {
        this.message = errorCode.message
        this.status = errorCode.status
        this.errors = mutableListOf()
    }

    companion object {

        fun of(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(errorCode)
        }

        fun of(errorCode: ErrorCode, message: String): ErrorResponse {
            return ErrorResponse(errorCode, message)
        }

        fun of(errorCode: ErrorCode, bindingResult: BindingResult): ErrorResponse {
            return ErrorResponse(errorCode, FieldError.of(bindingResult))
        }

        fun of(e: MethodArgumentTypeMismatchException): ErrorResponse {
            val value: String? = e.value?.let { it.toString() } ?: ""
            val errors = FieldError.of(e.name, value, e.errorCode)
            return ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, errors)
        }
    }

    data class FieldError(
        var field: String? = null,
        var value: String? = null,
        var reason: String? = null
    ) {
        companion object {
            fun of(bindingResult: BindingResult): MutableList<FieldError> {
                val fieldErrors = bindingResult.fieldErrors
                return fieldErrors.map {
                    FieldError(it.field, it.rejectedValue?.toString(), it.defaultMessage)
                }.toMutableList()
            }

            fun of(field: String, value: String?, reason: String): MutableList<FieldError> {
                val fieldErrors = mutableListOf<FieldError>()
                fieldErrors.add(FieldError(field, value, reason))
                return fieldErrors
            }

            fun of(field: String, reason: String): FieldError {
                return FieldError(field, null, reason)
            }
        }
    }
}
