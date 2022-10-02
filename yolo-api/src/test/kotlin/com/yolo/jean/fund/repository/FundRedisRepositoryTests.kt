package com.yolo.jean.fund.repository

import com.yolo.jean.domain.fund.entity.Fund
import com.yolo.jean.domain.fund.repository.FundRedisRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import java.util.Objects
import java.util.concurrent.*

@SpringBootTest
class FundRedisRepositoryTests {

    @Autowired
    lateinit var fundRedisRepo: FundRedisRepository

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>

    @Test
    @DisplayName("펀드를 Redis에 저장한다")
    fun saveFundWithRedis() {

        val funds = (1..10).map {
            Fund(
                id = it.toLong()
            )
        }
        fundRedisRepo.saveAll(funds)
    }

    @Test
    @DisplayName("Strings 타입의 데이터를 저장한다.")
    fun getRedisStringsValue() {

        // given
        val stringValueOperations = redisTemplate.opsForValue()

        // when
        stringValueOperations.set("department", "코어서비스")
        stringValueOperations.set("hobby", "농구")

        println("부서: ${stringValueOperations.get("department")}")
        println("취미: ${stringValueOperations.get("hobby")}")

        // then
        assertThat(stringValueOperations["department"]).isEqualTo("코어서비스")
        assertThat(stringValueOperations["hobby"]).isEqualTo("농구")
    }

    @Test
    @DisplayName("multi thread를 이용하여 Redis에 저장한다.")
    fun saveFundsByThreads() {

        val fund = Fund(20)
        fundRedisRepo.save(fund)

        println("name: ${fund.name}")
        println("STARTED")

        val executor = Executors.newFixedThreadPool(100)

        for (i in 1..100) {
            executor.submit {

                fund.name = "ThreadName - $i"
                println("ThreadName: ${Thread.currentThread().name}, ${fund.name}")
                fundRedisRepo.save(fund)

                val findFund = fundRedisRepo.findByIdOrNull(20)

                if (Objects.isNull(findFund)) {
                    println("NOT FOUND - ${Thread.currentThread().name}")
                } else {
                    println("FOUND - ${Thread.currentThread().name}, id: ${findFund!!.id}, name: ${findFund.name}")
                }
            }
        }

        TimeUnit.SECONDS.sleep(10)
    }
}
