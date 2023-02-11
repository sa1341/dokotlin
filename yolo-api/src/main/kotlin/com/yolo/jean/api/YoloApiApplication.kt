package com.yolo.jean.api

import com.yolo.jean.domain.config.EnableYoloDomain
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@EnableCaching
@EnableEurekaClient
@EnableYoloDomain
@SpringBootApplication
class YoloApiApplication

fun main(args: Array<String>) {
    runApplication<YoloApiApplication>(*args)
}
