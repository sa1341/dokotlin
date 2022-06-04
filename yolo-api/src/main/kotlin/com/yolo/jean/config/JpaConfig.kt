/*
package com.yolo.jean.config

import com.querydsl.jpa.impl.JPAQueryFactory
import com.yolo.jean.domain.common.logger
import com.yolo.jean.domain.config.EnableYoloDomain
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
@EnableYoloDomain
class JpaConfig(
    private val em: EntityManager
) {

    private val log by logger()

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        log.debug("####### JPAQueryFactory initialize #######")
        return JPAQueryFactory(em)
    }
}
*/
