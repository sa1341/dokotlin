package com.yolo.dokotlin.order.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import com.yolo.dokotlin.member.entity.Member
import com.yolo.dokotlin.order.model.OrderStatus
import javax.persistence.*

@Table(name = "orders")
@Entity
class Order private constructor(_member: Member, _orderItem: MutableList<OrderItem>):BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order?_id")
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member = _member

    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.ORDER

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = _orderItem

    companion object {
        fun of(member: Member, orderItem: MutableList<OrderItem>): Order {
            return Order(member, orderItem)
        }
     }

    fun cancelOrder() {
        orderStatus = OrderStatus.CANCEL
    }
}
