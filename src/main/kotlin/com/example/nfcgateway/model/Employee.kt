package com.example.nfcgateway.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "employee")
data class Employee(
    @Id
    val id: String? = null,
    var phoneNfcId: String?, // NFC ID from the employee's phone
    var name: String,
    var email: String,
    var password: String, // hased passsword
    @Indexed(unique = true)
    val employeeId: String,// Unique employee ID
    var department: String,
    var assignedProjects: List<String>, // List of assigned projects
    val isAdmin: Boolean = false // To differentiate between admin and employee
)
