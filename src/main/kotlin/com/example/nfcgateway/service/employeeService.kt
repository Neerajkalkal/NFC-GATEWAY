package com.example.nfcgateway.service

import com.example.nfcgateway.dto.NfcRequest
import com.example.nfcgateway.dto.loginRequest
import com.example.nfcgateway.model.Employee
import com.example.nfcgateway.repository.EmployeeRepository
import com.example.nfcgateway.util.JWTService
import org.apache.http.auth.InvalidCredentialsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class employeeService(
    private val employeeRepository: EmployeeRepository,
   @Autowired
   private val jwtService: JWTService
)
{
    private val passwordEncoder = BCryptPasswordEncoder()

    // Register Employee (Admin Functionality)
    fun reqisterEmployee( val employee: Employee) {
            employee.password = passwordEncoder.encode(employee.password)
            employeeRepository.save(employee)
    }

    // Login with Email/Password
    fun login(loginRequest: loginRequest): String{
        val employee = employeeRepository.findByEmail(loginRequest.email)
            ?: throw EmployeeNotFoundException("Employee not found")
        if (!passwordEncoder.matches(loginRequest.password, employee.password)) {
            throw InvalidCredentialsException("Invalid credentials")
        }
        return jwtService.generateToken(employee.email)
    }

    // Login with NFC
    fun loginWithNfc(nfcRequest: NfcRequest): String {
        val employee = employeeRepository.findByPhoneNfcId(nfcRequest.phoneNfcId)
            ?: throw EmployeeNotFoundException("Invalid NFC ID")
        return jwtService.generateToken(employee.email)
    }

}
