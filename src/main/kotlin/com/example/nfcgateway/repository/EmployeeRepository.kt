package com.example.nfcgateway.repository

import com.example.nfcgateway.model.Employee
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : MongoRepository<Employee , String>{
    fun findByEmail(email: String) : Employee?
    fun findByPhoneNfcId(phoneNfcId: String) : Employee?
}