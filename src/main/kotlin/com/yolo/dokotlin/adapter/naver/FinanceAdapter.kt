package com.yolo.dokotlin.adapter.naver

import com.yolo.dokotlin.global.config.logger.logger
import com.yolo.dokotlin.stock.dto.StockInfo
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class FinanceAdapter (private val financeRestTemplate: RestTemplate) {

    private val log by logger()

    fun getStockInfo(code: String): ResponseEntity<StockInfo> {

        log.info("종목코드: $code")

        val build = UriComponentsBuilder.fromPath("/api/realtime/domestic/stock")
            .path("/{code}")
            .build()
            .expand(code)

        val financeUrl = build.toUriString()
        log.info("url: $financeUrl")

        val response = financeRestTemplate.exchange(financeUrl, HttpMethod.GET, null,
            object : ParameterizedTypeReference<StockInfo>() {})

        log.info("response: $response")

        return response
    }
}
