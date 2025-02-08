package com.example.nfcgateway.controller

import com.example.nfcgateway.model.employee
import com.example.nfcgateway.repository.NfcRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/employee")
class employeeController (
    private val NfcRepository : NfcRepository
){
    @GetMapping
    fun createEmployee(): String{
        NfcRepository.save(
            employee(
                id = "",
                nfcId = "",
                name = "Neeraj",
                email = "kalkalneeraj",
                password = "Neeraj@123",
                role = "developer",
                department =  "enginer",
                assignedProjects = emptyList(),
            )
        )
        return "OK"
    }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable employeeId: String) : employee?{
        return NfcRepository.findById(employeeId).orElse(null)
    }
}