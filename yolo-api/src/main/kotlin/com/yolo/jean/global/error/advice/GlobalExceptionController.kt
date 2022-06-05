package com.yolo.jean.global.error.advice

import com.yolo.jean.domain.common.logger
import com.yolo.jean.global.error.exception.BusinessException
import com.yolo.jean.global.error.exception.ErrorResponse
import com.yolo.jean.global.error.exception.constants.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.concurrent.RejectedExecutionException
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionController {

    private val log by logger()

    @ExceptionHandler(value = [BusinessException::class])
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        log.error("handleBusinessException", e)
        val errorCode = e.errorCode
        val errorResponse = ErrorResponse.of(errorCode, e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("handleException", e)
        val errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    @ExceptionHandler(value = [BindException::class])
    fun handleBindException(e: BindException): ResponseEntity<ErrorResponse> {
        log.error("handleBindException", e)
        val errorResponse = e.message?.let { ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, it) }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    // @Valid 유효성을 통과하지 못할 경우 발생함.
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.error("MethodArgumentNotValidException", e)
        val errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_VALID, e.bindingResult)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    // Number 타입이 아닌 String 타입으로 요청이 들어올 경우 발생함
    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        log.error("MethodArgumentTypeMismatchException", e)
        val errorResponse = ErrorResponse.of(e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    // 메서드 파라미터나 리턴값이 유효하지 않으면 발생함
     @ExceptionHandler(value = [ConstraintViolationException::class])
     fun handleConstraintViolationException(e: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<MutableList<ErrorResponse.FieldError>> {
        log.error("handleConstraintViolationException", e)
         val errors =  mutableListOf<ErrorResponse.FieldError>()
         e.constraintViolations.forEach {
             val field = it.propertyPath.last().name
             val message = it.message
             errors.add(ErrorResponse.FieldError.of(field,message))
         }
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
     }

    @ExceptionHandler(value = [RejectedExecutionException::class])
    fun handleRejectedException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("handleRejectedExecutionException", e)
        val errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
