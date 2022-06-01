package com.yolo.dokotlin.global.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

@Service
class AsyncService {

    var log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * @Async 어노테이션은 Spring CGLIB 프록시 패턴으로 스레드 풀에 작업을 요청하기 때문에 같은 클래스 안에서
     * @Async가 어노테이션이 적용되지 않은 메서드 호출 시에는 비동기로 수행안되기 때문에 유의해야합니다.
     */
    @Async
    fun asyncMethod(i: Int) {
        try {
            TimeUnit.MILLISECONDS.sleep(500)
            log.info("[AsyncMethod] - $i")
        } catch (e: InterruptedException) {
            log.error("ERROR: $e")
        }
    }

    /**
     *  서비스 호출 시 지정한 예외 발생시에 Backoff를 주어서 3초 후에 한번 더 리트라이 하도록 설정
     */
    @Retryable(value = [IllegalStateException::class], backoff = Backoff(delay = 3000))
    fun retry(): Int {
        val rand: Int = Random().nextInt()

        log.info("value: $rand")

        if (rand % 2 == 0) { // 1/2 확률로 에러가 발생 한다.
            log.error("ERROR ===>>>")
            throw IllegalStateException("FFFFFF")
        }

        log.info("Completed")

        return 0;
    }
}
