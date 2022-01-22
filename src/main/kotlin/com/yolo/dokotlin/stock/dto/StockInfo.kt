package com.yolo.dokotlin.stock.dto

import com.yolo.dokotlin.stock.domain.Stock

data class StockInfo(
    val pollingInterval: Long,
    val datas: List<Stock> = arrayListOf(),
    val time: String
)

