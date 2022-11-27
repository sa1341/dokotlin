package com.yolo.jean.customer.api

import com.yolo.jean.customer.domain.Customer
import com.yolo.jean.customer.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class CustomerApi {

    @Autowired
    lateinit var customerService: CustomerService

    @GetMapping(value = ["/customer/{id}"])
    fun getCustomer(@PathVariable id: Int) = customerService.getCustomer(id)

    @PostMapping(value = ["/customer/"])
    fun createCustomer(@RequestBody customer: Customer) {
        customerService.createCustomer(customer)
    }

    @PutMapping(value = ["/customer/{id}"])
    fun updateCustomer(@PathVariable id:Int, @RequestBody customer: Customer) {
        customerService.updateCustomer(id, customer)
    }

    @DeleteMapping(value = ["/customer/{id}"])
    fun deleteCustomer(@PathVariable id:Int) {
        customerService.deleteCustomer(id)
    }

    @GetMapping(value = ["/customers"])
    fun getCustomers(@RequestParam(required = false, defaultValue = "")
    nameFilter: String) = customerService.searchCustomers(nameFilter)
}
