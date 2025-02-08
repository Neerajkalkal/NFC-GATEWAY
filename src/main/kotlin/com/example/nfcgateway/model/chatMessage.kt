package com.example.nfcgateway.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "messages")
data class chatMessage(
    @Id
    val id: String? = null,
    val projectId: String,
    val senderId: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)
