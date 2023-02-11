package com.yolo.jean.api.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Aspect
@Component
class LogAspect {

    private val log = LoggerFactory.getLogger(this.javaClass)

    // PointCut
    @Pointcut("execution(public * com.yolo.jean.board.service..*(..))")
    fun publicTarget() {}

    // Advice
    @Around("publicTarget()")
    fun calcPerformanceAdvice(pjp: ProceedingJoinPoint): Any {
        log.info("성능 측정을 시작합니다.")

        val sw = StopWatch()
        sw.start()

        val result = pjp.proceed()
        log.info("result: $result")

        sw.stop()
        log.info("성능 측정이 종료되었습니다.")
        log.info("걸린시간: {} ms", sw.lastTaskTimeMillis)
        return result
    }
}
