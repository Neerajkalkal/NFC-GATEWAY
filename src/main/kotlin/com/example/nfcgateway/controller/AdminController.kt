package com.example.nfcgateway.controller

import com.example.nfcgateway.model.Employee
import com.example.nfcgateway.service.AdminService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController (
    private val adminService: AdminService
)
{
    @PostMapping("/create-employee")
    fun createEmployee(@RequestBody employee: Employee): ResponseEntity<String> {
        adminService.createEmployee(employee)
        return ResponseEntity.ok("Employee created successfully")
    }

    @GetMapping("/employees")
    fun getAllEmployees(): ResponseEntity<List<Employee>> {
        val employees = adminService.getAllEmployees()
        return ResponseEntity.ok(employees)
    }
}