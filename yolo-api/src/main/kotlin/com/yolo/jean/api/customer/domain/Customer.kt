package com.yolo.jean.api.customer.domain

data class Customer(
    val id: Int = 0,
    val name: String = "",
    var telephone: Telephone? = null
)
