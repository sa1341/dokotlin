package com.yolo.jean

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@EnableCaching
@SpringBootApplication
class YoloApiApplication

fun main(args: Array<String>) {
    runApplication<YoloApiApplication>(*args)
}
