package com.yolo.jean.domain.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class QueryDslConfiguration(
    private val em: EntityManager
) {

    @Bean
    fun jpaQueryFactory() = JPAQueryFactory(em)
}
