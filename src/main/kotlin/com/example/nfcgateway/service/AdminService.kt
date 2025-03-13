package com.example.nfcgateway.service

import com.example.nfcgateway.Exception.DuplicateEmailException
import com.example.nfcgateway.Exception.EmailSendException
import com.example.nfcgateway.Exception.InvalidEmailFormatException
import com.example.nfcgateway.dto.CreateEmployeeRequest
import com.example.nfcgateway.model.Employee
import com.example.nfcgateway.repository.EmployeeRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern

@Service
class AdminService(
    private val employeeRepository: EmployeeRepository,
    private val emailService: EmailService,
    private val passwordService : PasswordService,
    private val idGenerator: EmployeeIdGeneratorService
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    @Transactional
    // Create Employee (Admin Functionality)
    fun createEmployee(request: CreateEmployeeRequest ) : Employee {

        // Validate email format
        if (!isValidEmail(request.email)) {
            throw InvalidEmailFormatException("Invalid email address format")
        }

        // Check for existing email
        if (employeeRepository.existsByEmail(request.email)) {
            throw DuplicateEmailException("Email already exists")
        }

        val employeeId = idGenerator.generateNextEmployeeId()
        val tempPassword = passwordService.generatePassword()
        // Hash the password before saving
        val hashedPassword = passwordEncoder.encode(tempPassword)
        val employee = Employee(
            employeeId = employeeId,
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
        emailService.sendCredentialsEmail(request.email,tempPassword , employeeId , request.name)
    }catch (ex: Exception){
        // Rollback employee creation if email fails
        employeeRepository.delete(savedEmployee)
        throw EmailSendException("Employee creation failed: ${ex.message}")
    }

        return savedEmployee
    }

    // Get All Employees (Admin Functionality)
    fun getAllEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }


    // delete an employee
    fun deleteEmployee(employeeId: String): String {
        val employee = employeeRepository.findByEmployeeId(employeeId)
        ?: throw Exception("Employee with id $employeeId does not exist")
        employeeRepository.delete(employee)
        return "Employee deleted successfully"
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return Pattern.matches(emailRegex, email)
    }
}