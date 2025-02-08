package com.example.nfcgateway.model

data class excelReport(
    val employeeName: String,
    val employeeId: String,
    val date: String,
    val checkInTime: String,
    val checkOutTime: String?,
    val totalWorkingHours: Double,
    val status: String
)
