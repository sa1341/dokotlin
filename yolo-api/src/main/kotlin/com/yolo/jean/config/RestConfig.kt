package com.yolo.jean.config

import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

const val MAX_TOTAL_CONNECTION = 200
const val MAX_PER_ROUTE = 20

@Configuration
class RestConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate(getHttpRequestFactory())
    }

    private fun getHttpRequestFactory(): HttpComponentsClientHttpRequestFactory {

        val factory = HttpComponentsClientHttpRequestFactory()

        val cm = PoolingHttpClientConnectionManager()
        cm.maxTotal = MAX_TOTAL_CONNECTION
        cm.defaultMaxPerRoute = MAX_PER_ROUTE

        val config = RequestConfig.custom()
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .setSocketTimeout(5000).build()

        val client = HttpClients.custom()
            .setConnectionManager(cm)
            .setDefaultRequestConfig(config)
            .build()

        factory.httpClient = client

        return factory
    }
}
