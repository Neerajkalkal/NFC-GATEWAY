package com.example.nfcgateway.controller

import com.example.nfcgateway.dto.NfcRequest
import com.example.nfcgateway.dto.loginRequest
import com.example.nfcgateway.model.Employee
import com.example.nfcgateway.repository.EmployeeRepository
import com.example.nfcgateway.service.employeeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/employee")
class employeeController (
   private val employeeService: employeeService
){
   @PostMapping("/login")
   fun login(@RequestBody loginRequest: loginRequest): ResponseEntity<String> {
       val token = employeeService.login(loginRequest)
       return ResponseEntity.ok(token)
   }

    // Update employee details
//    @PutMapping("/{employeeId}")
//    fun updateEmployee(
//        @PathVariable employeeId: String,
//        @RequestBody updatedEmployee: Employee
//    ): ResponseEntity<Employee> {
//        val employee = employeeService.updateEmployee(employeeId, updatedEmployee)
//        return ResponseEntity.ok(employee)
//    }

//    @PostMapping("/nfc-login")
//    fun nfcLogin(@RequestBody nfcRequest: NfcRequest): ResponseEntity<String> {
//        val token = employeeService.loginWithNfc(nfcRequest)
//        return ResponseEntity.ok(token.toString())
//    }
}