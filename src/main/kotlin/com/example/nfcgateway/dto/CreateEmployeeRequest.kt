package com.example.nfcgateway.dto

data class CreateEmployeeRequest(
    val employeeId: String,
    val name: String,
    val email: String,
    val password: String, // Plaintext password (sent via email)
    val phoneNfcId: String,
    val department: String,
    val assignedProjects: List<String>,
    val isAdmin: Boolean = false
)