package com.example.nfcgateway.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "meeting")
data class meeting(
    @Id
    val id: String? = null,
    val title: String,
    val description: String?,
    val scheduledTime: Long,
    val organizerId: String,
    val participantIds: List<String>,
    val meetingLink: String? = null // For Google Meet, Zoom, etc.
)
