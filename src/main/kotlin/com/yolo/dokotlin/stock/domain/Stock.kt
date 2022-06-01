package com.yolo.dokotlin.stock.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Stock (
    val itemCode: String,
    val stockName: String,
    val closePrice: String,
    val compareToPreviousClosePrice: String,
    val compareToPreviousPrice: CodeType,
    val openPrice: String,
    val highPrice: String,
    val lowPrice: String,
    val accumulatedTradingVolume: String,
    val accumulatedTradingValue: String,
    val marketStatus: String,
    val localTradedAt: String,
    val symbolCode: String,
    val currencyType: CodeType
)
