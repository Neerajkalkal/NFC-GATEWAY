package com.example.nfcgateway.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "attendance")
data class attendance(
    @Id
    var id: String? = null,
    val employeeId: String,
    val checkInTime: Long,
    val checkOutTime: Long? = null,
    val location: String,
    val status : String, // "PRESENT", "LATE", "ABSENT"
    val workingHours : Double? = null,
)
