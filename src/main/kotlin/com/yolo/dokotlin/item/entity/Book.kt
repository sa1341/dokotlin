package com.yolo.dokotlin.item.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn

@PrimaryKeyJoinColumn(name = "book_id")
@DiscriminatorValue(value = "B")
@Entity
class Book private constructor(_name: String, _price: Int, _stock: Int, _author: String, isbn: String): Item(_name, _price, _stock) {

    var author: String = _author
    var isbn: String = isbn

    companion object {
        fun of(_name: String, _price: Int, _stock: Int, _author: String, isbn: String): Book {
            return Book(_name, _price, _stock, _author, isbn)
        }
    }
}
