package com.example.nfcgateway.repository

import com.example.nfcgateway.model.Employee
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : MongoRepository<Employee , String>{
    fun existsByEmail(email: String): Boolean
    fun findByEmployeeId(employeeId: String): Employee?
    fun findByEmail(email: String) : Employee?
//    fun findByPhoneNfcId(phoneNfcId: String) : Employee?
}