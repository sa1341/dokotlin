package com.yolo.jean.api.fund.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


data class FundProductDto(
    @field:NotBlank
    val fundCod: String
)

data class FundInfo(
    @field:Size(min = 4, max = 100, message = "400")
    val fundCod: String,
    @field:Min(1000)
    @field:Max(10000)
    val rqsAmt: Int
)
