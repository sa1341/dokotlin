package com.yolo.dokotlin.web

import com.yolo.dokotlin.global.config.MyConfig
import com.yolo.dokotlin.global.service.AsyncService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WebApi(
   private val asyncService: AsyncService,
   private val myConfig: MyConfig
) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/async-test")
    fun testAsync() {

        logger.info("TEST ASYNC")
        logger.info("HOST: ${myConfig.host}, PORT: ${myConfig.port}")

        for (i in 1..50) {
            asyncService.asyncMethod(i)
        }
    }

    @GetMapping("/retry-test")
    fun retryTest() {
        logger.info("Retry Test")
        asyncService.retry();
    }
}
