package com.yolo.dokotlin.employee.repository

import com.yolo.dokotlin.employ.entity.Employee
import com.yolo.dokotlin.employ.entity.EmployeeId
import com.yolo.dokotlin.employ.repository.EmployeeRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class EmployeeRepositoryTests {

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @DisplayName("unique한 복합키를 생성하여 사원 테이블에 사원을 저장하는 테스트")
    @Test
    fun 복합키를_생성하여_사원을_저장한다() {

        // given
        var employeeId = EmployeeId.of(202106305959, "임준영")
        var employee = Employee.of(employeeId, "010-7900-7714")

        // when
        employeeRepository.save(employee)

        // then
        var findEmployee = employeeRepository.findByIdOrNull(employeeId)

        assertThat(findEmployee!!.employeeId.name).isEqualTo("임준영")
    }
}
