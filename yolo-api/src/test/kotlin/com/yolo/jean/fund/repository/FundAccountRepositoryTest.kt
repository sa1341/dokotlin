package com.yolo.jean.fund.repository

import com.yolo.jean.domain.fund.entity.FundAccount
import com.yolo.jean.domain.fund.entity.FundPrimaryKeys
import com.yolo.jean.domain.fund.repository.FundAccountRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FundAccountRepositoryTest {

    @Autowired
    lateinit var fundAccountRepository: FundAccountRepository

    @BeforeEach
  /*  fun createFundAccountTest() {
        // given
        val fundPrimaryKeys = FundPrimaryKeys("02000162758", 1)
        val fundAccount = FundAccount(fundPrimaryKeys, "207001", 10000)

        // when
        fundAccountRepository.save(fundAccount)
    }*/

    @Test
    @DisplayName(value = "펀드 계좌번호를 조회한다.")
    fun selectFundAccount() {
        val fundPrimaryKeys = FundPrimaryKeys("02000162758", 1)
        val fundAccount = fundAccountRepository.findById(fundPrimaryKeys).orElseThrow { RuntimeException("해당 계좌는 존재하지않습니다.") }
        assertThat(fundAccount.fundCod).isEqualTo("207001")
    }
}
