package com.yolo.jean.domain.fund.entity

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
class FundAccount(
    @EmbeddedId
    val fundPrimaryKeys: FundPrimaryKeys,
    @Column(name = "fnd_cod")
    val fundCod: String,
    @Column(name = "amount")
    var amount: Long
) {
}
