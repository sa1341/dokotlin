package com.yolo.dokotlin.global.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class QueryDslConfiguration(@Autowired val em: EntityManager) {
    @Bean
    fun jpaQueryFactory() = JPAQueryFactory(em)
}
