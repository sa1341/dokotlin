package com.yolo.jean.api.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class LoggingConfig {
    @Bean
    fun requestLoggingFilter(): OncePerRequestFilter {
        return RequestLoggingFilter()
    }
}

class RequestLoggingFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("Filter Start!")

        val cachingRequest = ContentCachingRequestWrapper(request)
        val cachingResponse = ContentCachingResponseWrapper(response)

        doFilter(filterChain, cachingRequest, cachingResponse)
    }

    private fun doFilter(
        filterChain: FilterChain,
        cachingRequest: ContentCachingRequestWrapper,
        cachingResponse: ContentCachingResponseWrapper
    ) {
        filterChain.doFilter(cachingRequest, cachingResponse)
        log.info (
            """RESPONSE
                | Request URI : ${cachingRequest.requestURI}
                | Request Method : ${cachingRequest.method}
                | Response Status : ${cachingResponse.status}
            """.trimMargin()
        )
        cachingResponse.copyBodyToResponse()
    }
}
