package com.yolo.jean.customer.service

import com.yolo.jean.customer.domain.Customer

interface CustomerService {
    fun getCustomer(id: Int): Customer?
    fun createCustomer(customer: Customer)
    fun deleteCustomer(id: Int)
    fun updateCustomer(id: Int, customer: Customer)
    fun searchCustomers(nameFilter: String): List<Customer>
}
