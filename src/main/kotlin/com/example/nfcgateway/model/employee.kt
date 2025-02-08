package com.example.nfcgateway.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "employee")
data class employee(
    @Id
    val id: String? = null,
    val nfcId : String ? = null,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val department: String,
    val assignedProjects : List<String> = listOf(),
//    val createdAt : Long = System.currentTimeMillis(),
)
