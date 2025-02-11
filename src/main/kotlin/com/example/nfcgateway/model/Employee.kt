package com.example.nfcgateway.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "employee")
data class Employee(
    @Id
    val id: String? = null,
    val phoneNfcId: String, // NFC ID from the employee's phone
    var name: String,
    var email: String,
    var password: String, // hased passsword
    val employeeId: String,// Unique employee ID
    val role: String = "EMPLOYEE",
    var department: String,
    var assignedProjects: List<String>, // List of assigned projects
    val isAdmin: Boolean = false // To differentiate between admin and employee
)
