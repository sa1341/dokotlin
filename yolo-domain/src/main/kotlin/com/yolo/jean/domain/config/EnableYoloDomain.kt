package com.yolo.jean.domain.config

import org.springframework.context.annotation.Import
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FILE

@Target(CLASS, FILE)
@Retention(RUNTIME)
@MustBeDocumented
@Import(
    YoloJpaConfig::class,
    YoloDatasourceConfig::class,
    QueryDslConfiguration::class
)
annotation class EnableYoloDomain()
