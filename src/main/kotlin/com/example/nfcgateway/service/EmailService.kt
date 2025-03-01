package com.example.nfcgateway.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    fun sendCredentialsEmail(toEmail: String, password: String, employeeId: String) {
        val message = SimpleMailMessage().apply {
            setTo(toEmail)
            subject = "Your NFC Gateway Account Credentials"
            text = """
                Your account has been created!
                Email: $toEmail
                Password: $password
                EmployeeId: $employeeId
            """.trimIndent()
        }
        try {
            mailSender.send(message)
        }catch (e: Exception){
            throw RuntimeException("Something went wrong : ${e.message}")
        }
    }
}