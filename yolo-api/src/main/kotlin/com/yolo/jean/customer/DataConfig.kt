package com.yolo.jean.customer

import com.yolo.jean.customer.domain.Customer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap

@Configuration
class DataConfig {

    companion object {
        val initialCustomers = arrayOf(
            Customer(1, "Kotlin")
        , Customer(2, "Spring")
        , Customer(3, "Microservice"))
    }


    @Bean
    fun customers() = ConcurrentHashMap<Int,
            Customer>(initialCustomers.associateBy(Customer::id))
}


