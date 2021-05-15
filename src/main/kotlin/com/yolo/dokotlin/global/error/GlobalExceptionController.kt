package com.yolo.dokotlin.global.error

import com.yolo.dokotlin.global.error.exception.BusinessException
import com.yolo.dokotlin.global.error.exception.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionController {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(value = [BindException::class])
    fun handleBindException(e: BindException): ResponseEntity<ErrorResponse> {
        logger.error("handleBindException", e)
        val errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.bindingResult)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.error("MethodArgumentNotValidException", e)
        val errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_VALID, e.bindingResult)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        logger.error("MethodArgumentTypeMismatchException", e)
        val errorResponse = ErrorResponse.of(e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolationException(e: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<MutableList<ErrorResponse.FieldError>> {
        logger.error("handleConstraintViolationException", e)
        val errors =  mutableListOf<ErrorResponse.FieldError>()
        e.constraintViolations.forEach {
            val field = it.propertyPath.last().name
            val message = it.message
            errors.add(ErrorResponse.FieldError.of(field,message))
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(value = [BusinessException::class])
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        logger.error("handleBusinessException", e)
        val errorCode = e.errorCode
        val errorResponse = ErrorResponse.of(errorCode, e.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("handleException", e)
        val errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
