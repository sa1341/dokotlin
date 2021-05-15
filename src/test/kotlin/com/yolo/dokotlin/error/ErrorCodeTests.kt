package com.yolo.dokotlin.error

import com.yolo.dokotlin.global.error.ErrorResponse
import com.yolo.dokotlin.global.error.exception.BusinessException
import com.yolo.dokotlin.global.error.exception.ErrorCode
import org.junit.jupiter.api.Test

class ErrorCodeTests {

    @Test
    fun 에러코드_내용들을_출력한다() {
        var error: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR
        println(error.name)
        println(error.status)
        println(error.message)
    }

    @Test
    fun 필드에러_내용을_출력한다() {
        var rejectedValue: Any? = null
        rejectedValue = "Welcome to Kotlin!!"

        val fieldError = ErrorResponse.FieldError("name", rejectedValue?.toString()?: "hello", "입력값이 일치하지 않습니다.")
        println("fieldError: $fieldError")
    }

    @Test
    fun 비지니스_Exception_테스트() {

        val businessException = BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)
        val errorCode = businessException.errorCode
        println("errorCode: ${errorCode?.status}")
        println("businessException: ${businessException.message}")
    }
}
