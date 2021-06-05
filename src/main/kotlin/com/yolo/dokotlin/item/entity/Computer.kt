package com.yolo.dokotlin.item.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn

@PrimaryKeyJoinColumn(name = "computer_id")
@DiscriminatorValue(value = "C")
@Entity
class Computer private constructor(_name: String, _price: Int, _stock: Int, _brandName: String): Item(_name, _price, _stock){

    var brandName: String = _brandName

    companion object {
        fun of(_name: String, _price: Int, _stock: Int, _brandName: String): Computer {
            return Computer(_name, _price, _stock, _brandName)
        }
    }
}
