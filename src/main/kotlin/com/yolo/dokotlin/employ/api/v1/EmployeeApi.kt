package com.yolo.dokotlin.employ.api.v1

import com.yolo.dokotlin.employ.entity.Employee
import com.yolo.dokotlin.employ.entity.EmployeeId
import com.yolo.dokotlin.employ.repository.EmployeeRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/api/v1"])
@RestController
class EmployeeApi(private val employeeRepository: EmployeeRepository) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping(path = ["/employees"])
    fun save() {
        var employeeId = EmployeeId.of(202106305959, "임준영")
        var employee = Employee.of(employeeId, "010-7900-7714")

        employeeRepository.save(employee)
    }

    @GetMapping(path = ["/employees"])
    fun findEmployee(@RequestParam(required = true, value = "empNo") empNo: Long, @RequestParam(required = true, value = "empName") empName: String) {
        logger.info("empNo: {}, name: {}", empNo, empName)
        var employeeId = EmployeeId.of(empNo, empName)

        val findEmployee = employeeRepository.findByIdOrNull(employeeId)
        logger.info("name: {}", findEmployee!!.phone)
    }
}
