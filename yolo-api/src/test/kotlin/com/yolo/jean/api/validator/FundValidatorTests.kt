package com.yolo.jean.api.validator

import com.yolo.jean.api.fund.dto.FundInfo
import com.yolo.jean.api.global.common.util.FundInputValidator
import com.yolo.jean.api.global.error.exception.BusinessException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FundValidatorTests {

    @Test
    fun validateTest() {
        // given
        val fundInfo = FundInfo("abc", 1000)

        // when

        Assertions.assertThrows(BusinessException::class.java) {
            FundInputValidator.validator(fundInfo)
        }

        // then
    }
}
