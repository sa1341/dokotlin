package com.yolo.jean.domain.fund.entity

import org.springframework.data.redis.core.RedisHash
import javax.persistence.Id

@RedisHash(value = "fund")
class Fund(
    @Id
    val id: Long? = null

) {
    var name = "name-$id"
}
