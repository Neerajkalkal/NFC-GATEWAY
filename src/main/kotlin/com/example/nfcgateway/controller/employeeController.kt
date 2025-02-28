package com.example.nfcgateway.controller

import com.example.nfcgateway.dto.NfcRequest
import com.example.nfcgateway.dto.changePasswordRequest
import com.example.nfcgateway.dto.loginRequest
import com.example.nfcgateway.service.EmployeeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/employee")
class employeeController (
   private val employeeService: EmployeeService
){
   @PostMapping("/login")
   fun login(@RequestBody loginRequest: loginRequest): ResponseEntity<String> {
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
}