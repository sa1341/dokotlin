package com.yolo.jean.customer.service

import com.yolo.jean.customer.DataConfig
import com.yolo.jean.customer.domain.Customer
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService {

    companion object {
        val initialCustomers = arrayOf(
            Customer(1, "Kotlin")
            , Customer(2, "Spring")
            , Customer(3, "Microservice"))
    }

    private val customers = ConcurrentHashMap<Int,
            Customer>(DataConfig.initialCustomers.associateBy(Customer::id))

    override fun getCustomer(id: Int) = customers[id]

    override fun createCustomer(customer: Customer) {
        customers[customer.id]  = customer
    }

    override fun deleteCustomer(id: Int) {
        customers.remove(id)
    }

    override fun updateCustomer(id: Int, customer: Customer) {
        deleteCustomer(id)
        createCustomer(customer)
    }

    override fun searchCustomers(nameFilter: String): List<Customer> {
        return customers.filter {
            it.value.name.contains(nameFilter, true)
        }.map(Map.Entry<Int, Customer>::value).toList()
    }
}
