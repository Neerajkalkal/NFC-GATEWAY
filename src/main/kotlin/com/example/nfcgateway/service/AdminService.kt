package com.example.nfcgateway.service

import com.example.nfcgateway.model.Employee
import com.example.nfcgateway.repository.EmployeeRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminService (
    private val employeeRepository: EmployeeRepository
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    // Create Employee (Admin Functionality)
    fun createEmployee(employee: Employee) : Employee {
        employee.password = passwordEncoder.encode(employee.password)
       return employeeRepository.save(employee)
    }

    // Get All Employees (Admin Functionality)
    fun getAllEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }
}