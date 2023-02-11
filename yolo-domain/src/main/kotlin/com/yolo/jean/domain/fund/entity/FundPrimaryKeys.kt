package com.yolo.jean.domain.fund.entity

import javax.persistence.Column
import javax.persistence.Embeddable
import java.io.Serializable

@Embeddable
class FundPrimaryKeys(
    @Column(name = "acno")
    val acno: String,
    @Column(name = "fnd_S")
    val fundS: Int
): Serializable {
}
