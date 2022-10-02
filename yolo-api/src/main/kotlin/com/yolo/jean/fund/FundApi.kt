package com.yolo.jean.fund

import com.yolo.jean.fund.dto.FundProductDto
import com.yolo.jean.global.common.model.CommonRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping("/{fundId}")
    fun getFundInfo(@PathVariable("fundId") fundId: String): ResponseEntity<String> {
        log.info("fundId: $fundId")

        if (fundId.equals("7333177")) {
            var name: String? = null
            return ResponseEntity.badRequest().body("fail")
        }

        return ResponseEntity.ok().body("success")
    }

    @PostMapping
    fun fundBuyRequest(@RequestBody commonRequest: CommonRequest<FundProductDto>): ResponseEntity<CommonRequest<FundProductDto>> {
        log.info("commonRequest = $commonRequest")

        return ResponseEntity.ok().body(commonRequest)
    }
}
