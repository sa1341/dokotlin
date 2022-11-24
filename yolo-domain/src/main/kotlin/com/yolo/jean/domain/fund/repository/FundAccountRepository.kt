package com.yolo.jean.domain.fund.repository

import com.yolo.jean.domain.fund.entity.FundAccount
import com.yolo.jean.domain.fund.entity.FundPrimaryKeys
import org.springframework.data.jpa.repository.JpaRepository

interface FundAcnoRepository: JpaRepository<FundAccount, FundPrimaryKeys> {
}
