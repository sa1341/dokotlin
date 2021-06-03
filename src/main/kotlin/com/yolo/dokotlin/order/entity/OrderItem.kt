package com.yolo.dokotlin.order.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import com.yolo.dokotlin.item.entity.Item
import javax.persistence.*

@Entity
class OrderItem private constructor(_item: Item, _price: Int, _quantity: Int): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item = _item

    var quantity: Int = _quantity

    var price: Int = _price

    companion object {
        fun createOrderItem(item: Item, price: Int, quantity: Int): OrderItem {
            item.removeStock(quantity)
            return OrderItem(item, price, quantity)
        }
    }

    fun getTotalPrice(): Int {
        return price * quantity
    }
}
