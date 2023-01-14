package com.yolo.jean.fund

import com.fasterxml.jackson.databind.ObjectMapper
import com.yolo.jean.fund.dto.FundBuyingReq
import com.yolo.jean.fund.dto.FundBuyingRes
import com.yolo.jean.fund.dto.FundProductDto
import com.yolo.jean.global.common.model.CommonRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RequestMapping("/api/v1/funds")
@RestController
class FundApi(
    @Qualifier(value = "restTemplate") private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(this.javaClass)
    @PostMapping("/buying-fund")
    fun buyFund(@RequestBody fundBuyingReq: FundBuyingReq): ResponseEntity<FundBuyingRes> {
        log.debug("FundBuyingRequest = $fundBuyingReq")
        val response = FundBuyingRes("success")
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("buying-fund-request")
    fun requestBuying(): ResponseEntity<String> {
        val fundBuyingReq = FundBuyingReq(accountNumber = "02000162758", fundCod = "2000502", name = "jean", age = 32)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val body = objectMapper.writeValueAsString(fundBuyingReq)
        val httpEntity = HttpEntity(fundBuyingReq, headers)

        val result = restTemplate.postForEntity("/buying-fund",
            httpEntity,
            FundBuyingRes::class.java)

        log.info("reulst = ${result}")

        return ResponseEntity.ok().body("success")
    }

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
