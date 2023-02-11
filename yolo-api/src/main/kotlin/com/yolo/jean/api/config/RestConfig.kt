package com.yolo.jean.api.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val MAX_TOTAL_CONNECTION = 200
const val MAX_PER_ROUTE = 20

@Configuration
class RestConfig(
    private val objectMapper: ObjectMapper
) {

    /**
     * json -> object로 역직렬화 시 object에 없는 필드가 있는 경우 예외없이 스킵하도록 설정함.
     */
    private val defaultObjectMapper = jacksonObjectMapper()
        .registerModule(
            JavaTimeModule().addSerializer(
                LocalDate::class.java,
                LocalDateSerializer(DateTimeFormatter.ISO_DATE)
            )
        )
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Bean
    fun restTemplate(): RestTemplate {
        val converter = MappingJackson2HttpMessageConverter(defaultObjectMapper)
        return RestTemplateBuilder()
            .rootUri("http://localhost:8082/api/v1/funds")
            .requestFactory { BufferingClientHttpRequestFactory(getHttpRequestFactory()) }
            .additionalMessageConverters(converter)
            .additionalInterceptors(ApiLoggingInterceptor(objectMapper))
            .build()
    }

    private fun getHttpRequestFactory(): HttpComponentsClientHttpRequestFactory {

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

        val factory = HttpComponentsClientHttpRequestFactory(client)
        factory.httpClient = client

        return factory
    }
}

private class ApiLoggingInterceptor(
    private val objectMapper: ObjectMapper
) : ClientHttpRequestInterceptor {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {

        val requestBody = byteArrayToJson(body)
        log.info(
            """REQUEST
            | Request URI : ${request.uri} 
            | Request Method : ${request.method} 
            | Request Header : ${request.headers} 
            | Request Body : $requestBody 
            """.trimMargin()
        )

        return execution.execute(request, body).also {
            log.info(
                """RESPONSE
                | Request URI : ${request.uri} 
                | Request Method : ${request.method} 
                | Request Header : ${request.headers} 
                | Request Body : $requestBody 
                | Response Status : ${it.statusCode} 
                | Response Body : ${byteArrayToJson(it.body.readBytes())}
                """.trimMargin()
            )
        }
    }

    private fun byteArrayToJson(byteArray: ByteArray) =
        byteArray.let {
            if (it.isEmpty()) {
                return ""
            }
            objectMapper.readTree(it).toString()
        }
}
