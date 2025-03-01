package com.example.nfcgateway.service

import com.example.nfcgateway.dto.CreateEmployeeRequest
import com.example.nfcgateway.model.Employee
import com.example.nfcgateway.repository.EmployeeRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val employeeRepository: EmployeeRepository,
    private val emailService: EmailService,
    private val passwordService : PasswordService
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    // Create Employee (Admin Functionality)
    fun createEmployee(request: CreateEmployeeRequest ) : Employee {

        val tempPassword = passwordService.generatePassword()
        // Hash the password before saving
        val hashedPassword = passwordEncoder.encode(tempPassword)
        val employee = Employee(
            employeeId = request.employeeId,
            name = request.name,
            email = request.email,
            password = hashedPassword,
            phoneNfcId = request.phoneNfcId,
            department = request.department,
            assignedProjects = request.assignedProjects,
            isAdmin = request.isAdmin
        )
       val savedEmployee= employeeRepository.save(employee)
    try {
        emailService.sendCredentialsEmail(request.email, tempPassword)
    }catch (ex: Exception){
        // Rollback employee creation if email fails
        employeeRepository.delete(savedEmployee)
        throw RuntimeException("Employee creation failed: ${ex.message}")
    }

        return savedEmployee
    }

    // Get All Employees (Admin Functionality)
    fun getAllEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }

    // update an employee
    fun updateEmployee(employeeId: String, updateEmployee: Employee): Employee {
        val existingEmployee = employeeRepository.findByEmployeeId(employeeId)
            ?: throw Exception("Employee with id $employeeId does not exist")

        existingEmployee.name = updateEmployee.name
        existingEmployee.email = updateEmployee.email
        existingEmployee.department = updateEmployee.department
        existingEmployee.assignedProjects = updateEmployee.assignedProjects
        existingEmployee.phoneNfcId= updateEmployee.phoneNfcId

        return employeeRepository.save(existingEmployee)
    }

    // delete an employee
    fun deleteEmployee(employeeId: String): String {
        val employee = employeeRepository.findByEmployeeId(employeeId)
        ?: throw Exception("Employee with id $employeeId does not exist")
        employeeRepository.delete(employee)
        return "Employee deleted successfully"
    }
}