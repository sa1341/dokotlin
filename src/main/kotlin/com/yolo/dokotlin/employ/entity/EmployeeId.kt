package com.yolo.dokotlin.employ.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class EmployeeId private constructor(_empNo: Long, _name: String): Serializable {

    @Column(name = "emp_no")
    var empNo: Long = _empNo

    @Column(name = "emp_name")
    var name: String = _name

    companion object {
        fun of(_empNo: Long, _name: String): EmployeeId {
            return EmployeeId(_empNo, _name)
        }
    }
}
