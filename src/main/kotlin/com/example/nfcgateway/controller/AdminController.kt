package com.example.nfcgateway.controller

import com.example.nfcgateway.dto.CreateEmployeeRequest
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
    @PostMapping("/create_employee")
    fun createEmployee(@RequestBody request: CreateEmployeeRequest): ResponseEntity<Employee> {
        val createdEmployee = adminService.createEmployee(request)
        return ResponseEntity.ok(createdEmployee)
    }


    // get all employee
    @GetMapping("/employees")
    fun getAllEmployees(): ResponseEntity<List<Employee>> {
        val employees = adminService.getAllEmployees()
        return ResponseEntity.ok(employees)
    }

//    // update an employee
//    @PutMapping("/employee/{employeeId}")
//    fun updateEmployee(
//        @PathVariable employeeId: String,
//        @RequestBody updateEmployee: Employee
//    ): ResponseEntity<Employee> {
//        val employee = adminService.updateEmployee(employeeId, updateEmployee)
//        return ResponseEntity.ok(employee)
//    }

    // delete an employee
    @DeleteMapping("/employee/{employeeId}")
    fun deleteEmployee(
        @PathVariable employeeId: String
    ): ResponseEntity<String> {
      val message=  adminService.deleteEmployee(employeeId)
        return ResponseEntity.ok(message)
    }
}