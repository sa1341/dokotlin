package com.yolo.jean.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "yolo.service")
class RedisConfigProps {
    lateinit var host: String
    lateinit var port: Number
}
