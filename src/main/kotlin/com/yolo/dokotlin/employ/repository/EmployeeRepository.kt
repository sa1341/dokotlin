package com.yolo.dokotlin.employ.repository

import com.yolo.dokotlin.employ.entity.Employee
import com.yolo.dokotlin.employ.entity.EmployeeId
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository: JpaRepository<Employee, EmployeeId>
