package com.example.nfcgateway.controller

import com.example.nfcgateway.dto.NfcRequest
import com.example.nfcgateway.dto.loginRequest
import com.example.nfcgateway.repository.EmployeeRepository
import com.example.nfcgateway.service.employeeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/employee")
class employeeController (
    private val employeeRepository: EmployeeRepository
){
   @PostMapping("/login")
   fun login(@RequestBody loginRequest: loginRequest): ResponseEntity<String> {
       val token = employeeRepository.findByEmail(loginRequest.email)
       return ResponseEntity.ok(token.toString())
   }

//    @PostMapping("/nfc-login")
//    fun nfcLogin(@RequestBody nfcRequest: NfcRequest): ResponseEntity<String> {
//        val token = employeeService.loginWithNfc(nfcRequest)
//        return ResponseEntity.ok(token.toString())
//    }
}