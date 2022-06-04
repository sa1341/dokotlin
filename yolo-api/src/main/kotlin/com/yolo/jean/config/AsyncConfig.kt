package com.yolo.jean.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurerSupport
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
@EnableAsync
class AsyncConfig: AsyncConfigurerSupport() {

    @Bean
    override fun getAsyncExecutor(): Executor {

        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize  = 300
        executor.setThreadNamePrefix("JEAN-CALM-")
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        executor.setWaitForTasksToCompleteOnShutdown(true) // 어플리케이션 종료 시 스레드 유실없이 처리
        executor.setAwaitTerminationSeconds(60) // shutdown 최대 60초 대기
        executor.initialize()

        return executor
    }
}
