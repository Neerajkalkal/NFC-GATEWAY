package com.example.nfcgateway.service

import com.example.nfcgateway.model.Employee
import com.example.nfcgateway.repository.EmployeeRepository
import com.example.nfcgateway.util.JWTService
import org.apache.http.auth.InvalidCredentialsException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class employeeService(
    private val employeeRepository: EmployeeRepository,
   private val jwtService: JWTService
) : UserDetailsService {
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun loadUserByUsername(email: String): UserDetails{
        val employee = employeeRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Employee not found with email: $email")
        return User(
            employee.email,
            employee.password,
            listOf(SimpleGrantedAuthority(
                if (employee.isAdmin) "ROLE_ADMIN" else "ROLE_EMPLOYEE"
            )
        ))
    }

    // Register Employee (Admin Functionality)
    fun reqisterEmployee(  employee: Employee): Employee {
            employee.password = passwordEncoder.encode(employee.password)
          return  employeeRepository.save(employee)
    }

    // Login with Email/Password
    fun login(email: String,password : String): String{
        val employee = employeeRepository.findByEmail(email)
            ?: throw Exception("Employee not found")
        if (!passwordEncoder.matches(password, employee.password)) {
            throw InvalidCredentialsException("Invalid credentials")
        }
        return jwtService.generateToken(
            User(employee.email, employee.password, getAuthorities(employee.isAdmin))
        )
    }

    // Login with NFC
    fun loginWithNfc(phoneNfcId : String): String {
        val employee = employeeRepository.findByPhoneNfcId(phoneNfcId)
            ?: throw Exception("Invalid NFC ID")
        return jwtService.generateToken(
            User(employee.email, employee.password, getAuthorities(employee.isAdmin))
        )
    }

    // Get authorities based on employee role
    private fun getAuthorities(isAdmin: Boolean): Collection<GrantedAuthority> {
        return if (isAdmin) {
            listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
        } else {
            listOf(SimpleGrantedAuthority("ROLE_EMPLOYEE"))
        }
    }

    // Get all employees (Admin functionality)
    fun getAllEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }

    // Get employee by ID
    fun getEmployeeById(employeeId: String): Employee {
        return employeeRepository.findById(employeeId)
            .orElseThrow {
                Exception("Employee not found with ID: $employeeId")
            }
    }

    // Update employee details
    fun updateEmployee(employeeId: String, updatedEmployee: Employee): Employee {
        val existingEmployee = employeeRepository.findById(employeeId)
            .orElseThrow {
                Exception("Employee not found with ID: $employeeId")
            }

        // Update fields
        existingEmployee.name = updatedEmployee.name
        existingEmployee.email = updatedEmployee.email
        existingEmployee.department = updatedEmployee.department
        existingEmployee.assignedProjects = updatedEmployee.assignedProjects

        // Save updated employee
        return employeeRepository.save(existingEmployee)
    }

    // Delete employee by ID
    fun deleteEmployee(employeeId: String) {
        if (!employeeRepository.existsById(employeeId)) {
            throw Exception("Employee not found with ID: $employeeId")
        }
        employeeRepository.deleteById(employeeId)
    }

}
