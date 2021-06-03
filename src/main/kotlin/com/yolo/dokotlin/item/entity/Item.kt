package com.yolo.dokotlin.item.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import com.yolo.dokotlin.global.error.exception.InvalidException
import javax.persistence.*

@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
abstract class Item(_name: String, _price: Int, _stock: Int): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long? = null
    var name: String = _name
    var price: Int = _price
    var stock: Int = _stock

    fun addStock(quantity: Int) {
        stock += quantity
    }

    fun removeStock(quantity: Int) {
        var remainStock = stock - quantity
        if (remainStock < 0) {
            throw InvalidException("need more stock")
        }
        stock = remainStock
    }
}
