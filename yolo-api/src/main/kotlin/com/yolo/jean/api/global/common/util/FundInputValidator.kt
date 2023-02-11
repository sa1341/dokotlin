package com.yolo.jean.api.global.common.util

import com.yolo.jean.api.global.error.exception.BusinessException
import com.yolo.jean.api.global.error.exception.constants.ErrorCode
import javax.validation.Validation

class FundInputValidator<out T> {

    companion object {
        fun <T> validator(t: T) {
            val validatorFactory = Validation.buildDefaultValidatorFactory()
            val validator = validatorFactory.validator

            val validate = validator.validate(t)

            if (validate.isNotEmpty()) {
                validate.forEach {
                    val status = it.message.toInt()
                    println("result = $it")

                    val errorCode = ErrorCode.values().find { it.status == status }
                    errorCode?.let { throw BusinessException("필수값입니다.", it) } ?: throw BusinessException("정의되지 않는 에러코드", ErrorCode.INVALID_INPUT_VALUE)
                }
            } else {
                println("검증간의 특이사항 없습니다!")
            }
        }
    }
}
