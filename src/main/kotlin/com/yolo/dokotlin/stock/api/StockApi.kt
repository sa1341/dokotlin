package com.yolo.dokotlin.stock.api

import com.yolo.dokotlin.adapter.naver.FinanceAdapter
import com.yolo.dokotlin.global.config.logger.logger
import com.yolo.dokotlin.stock.dto.StockInfo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/stock")
@RestController
class StockApi(private val financeAdapter: FinanceAdapter) {

    private val log by logger()

    @GetMapping
    fun getStockInfo(): ResponseEntity<StockInfo> {
        val code = "377300"
        return financeAdapter.getStockInfo(code)
    }
}
