package com.example.nfcgateway.service

import com.example.nfcgateway.dto.EmployeeDto
import com.example.nfcgateway.dto.changePasswordRequest
import com.example.nfcgateway.dto.loginRequest
import com.example.nfcgateway.model.Employee
import com.example.nfcgateway.repository.EmployeeRepository
import com.example.nfcgateway.util.JWTService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
   val jwtService: JWTService
) : UserDetailsService {
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun loadUserByUsername(email: String): UserDetails {
        val employee = employeeRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Employee not found with email: $email")
        return User(
            employee.email,
            employee.password,
            listOf(
                SimpleGrantedAuthority(
                    if (employee.isAdmin) "ROLE_ADMIN" else "ROLE_EMPLOYEE"
                )
            )
        )
    }

    // Register Employee (Admin Functionality)
    fun registerEmployee(employee: Employee): Employee {
        employee.password = passwordEncoder.encode(employee.password)
        return employeeRepository.save(employee)
    }

    // Login with Email/Password
    fun login(loginRequest: loginRequest): String {
        val employee = employeeRepository.findByEmail(loginRequest.email)
            ?: throw Exception("Employee not found")
        if (!passwordEncoder.matches(loginRequest.password, employee.password)) {
            throw Exception("Invalid credentials")
        }
        return jwtService.generateToken(employee)
    }

    // Login with NFC
    fun loginWithNfc(phoneNfcId: String): String {
        val employee = employeeRepository.findByPhoneNfcId(phoneNfcId)
            ?: throw Exception("Invalid NFC ID")
        return jwtService.generateToken(employee)
    }

    // change password
    fun changePassword(email: String, request: changePasswordRequest): String {
        val employee = employeeRepository.findByEmail(email)
            ?: throw Exception("Employee not found")
        if (!passwordEncoder.matches(request.oldPassword, employee.password)) {
            throw Exception("Old password is incorrect")
        }
        employee.password = passwordEncoder.encode(request.newPassword)
        employeeRepository.save(employee)
        return "Password changed successfully!"
    }

    fun getEmployeeByEmployeeId(email: String): EmployeeDto {
        val employee = employeeRepository.findByEmail(email)
            ?: throw RuntimeException("Employee not found")
        return EmployeeDto(
            employee.name,
            employee.department,
            employee.employeeId,
            employee.email
        )
    }
}
