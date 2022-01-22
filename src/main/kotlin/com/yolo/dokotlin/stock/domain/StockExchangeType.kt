package com.yolo.dokotlin.stock.domain

data class StockExchangeType(
    val code: String,
    val zoneId: String,
    val nationType: String,
    val delayTime: Long,
    val startTime: String,
    val endTime: String,
    val closePriceSendTime: String,
    val nameKor: String,
    val nameEng: String,
    val name: String
)
