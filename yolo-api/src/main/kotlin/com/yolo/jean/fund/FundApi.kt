package com.yolo.jean.fund

import com.yolo.jean.fund.dto.FundProductDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/funds")
@RestController
class FundApi {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/sync-fund")
    fun syncFundInfo(@RequestBody fundProductDto: FundProductDto): ResponseEntity<FundProductDto> {
        log.info("FundProductDto: [{}]", fundProductDto)
        return ResponseEntity.ok().body(fundProductDto)
    }
}
