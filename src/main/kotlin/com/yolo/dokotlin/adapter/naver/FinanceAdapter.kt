package com.yolo.dokotlin.adapter.naver

import com.yolo.dokotlin.global.config.logger.logger
import com.yolo.dokotlin.stock.dto.StockInfo
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import kotlin.jvm.Throws

@Component
class FinanceAdapter (
    private val financeRestTemplate: RestTemplate
) {

    private val log by logger()

    @Throws(IOException::class)
    fun getStockInfo(code: String): ResponseEntity<StockInfo> {

        log.info("종목코드: $code")

        val build = UriComponentsBuilder.fromPath("/api/realtime/domestic/stock")
            .path("/{code}")
            .build()
            .expand(code)

        val financialUrl = build.toUriString()
        log.info("financialUrl: [{}]", financialUrl)

        val response = financeRestTemplate.exchange(financialUrl, HttpMethod.GET, null,
            object : ParameterizedTypeReference<StockInfo>() {})

        log.info("response: [{}]", response)

        return response
    }
}
