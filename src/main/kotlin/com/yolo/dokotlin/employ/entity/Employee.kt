package com.yolo.dokotlin.employ.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import javax.persistence.EmbeddedId
import javax.persistence.Entity


@Entity
class Employee private constructor(_employeeId: EmployeeId, _phone: String): BaseTimeEntity() {

    @EmbeddedId
    var employeeId: EmployeeId = _employeeId

    var phone: String = _phone

    companion object {
        fun of(_employeeId: EmployeeId, _phone: String): Employee {
            return Employee(_employeeId, _phone)
        }
    }
}
