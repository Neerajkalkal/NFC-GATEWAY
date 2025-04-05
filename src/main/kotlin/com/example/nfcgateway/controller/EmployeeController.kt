package com.example.nfcgateway.controller

import com.example.nfcgateway.dto.*
import com.example.nfcgateway.repository.EmployeeRepository
import com.example.nfcgateway.service.EmployeeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/employee")
class EmployeeController(
    private val employeeService: EmployeeService,
    private val employeeRepository: EmployeeRepository
){
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: loginRequest): ResponseEntity<String> {
        println(loginRequest)
        val token = employeeService.login(loginRequest)
        return ResponseEntity.ok(token)
    }
    // login with nfc
    @PostMapping("/nfc-login")
    fun nfcLogin(@RequestBody nfcRequest: NfcRequest): ResponseEntity<String> {
        val token = employeeService.loginWithNfc(nfcRequest.toString())
        return ResponseEntity.ok(token)
    }

    // change password
    @PostMapping("/change-password")
    fun changePassword(
        @RequestBody request: changePasswordRequest,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<String> {
        val email = employeeService.jwtService.extractUsername(token.substring(7))
        val message = employeeService.changePassword(email, request)
        return ResponseEntity.ok(message)
    }

    @GetMapping("/{email}")
    fun getEmployeeByEmployeeId(@PathVariable email: String): ResponseEntity<EmployeeDto> {
        val employee = employeeService.getEmployeeByEmployeeId(email)
        return ResponseEntity.ok(employee)
    }


}