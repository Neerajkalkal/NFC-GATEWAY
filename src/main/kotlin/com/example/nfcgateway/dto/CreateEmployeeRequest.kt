package com.example.nfcgateway.dto

import org.springframework.data.mongodb.core.index.IndexOptions.Unique
import org.springframework.stereotype.Indexed

data class CreateEmployeeRequest(
    val employeeId: String,
    val name: String,
    val email: String,
    val phoneNfcId: String,
    val department: String,
    val assignedProjects: List<String>,
    val isAdmin: Boolean = false
)