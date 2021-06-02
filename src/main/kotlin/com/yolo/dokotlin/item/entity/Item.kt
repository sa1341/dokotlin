package com.yolo.dokotlin.item.entity

import javax.persistence.*

@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
abstract class Item(_name: String, _price: Number, _stock: Long) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long? = null
    var name: String = _name
    var price: Number = _price
    var stock: Long = _stock
}
