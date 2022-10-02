package com.yolo.jean.validator

import com.yolo.jean.fund.dto.FundInfo
import com.yolo.jean.global.common.util.FundInputValidator
import com.yolo.jean.global.error.exception.BusinessException
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
