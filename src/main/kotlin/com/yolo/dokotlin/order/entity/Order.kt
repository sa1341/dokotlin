package com.yolo.dokotlin.order.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import com.yolo.dokotlin.order.model.OrderStatus
import javax.persistence.*

@Table(name = "orders")
@Entity
class Order private constructor():BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus? = null

    companion object {
        fun of(): Order {

            val order =  Order()
            order.orderStatus = OrderStatus.ORDER


            return order
        }
     }
}
