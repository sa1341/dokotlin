package com.yolo.jean.domain.fund.repository

import com.yolo.jean.domain.fund.entity.Fund
import org.springframework.data.repository.CrudRepository

interface FundRedisRepository: CrudRepository<Fund, Long>
