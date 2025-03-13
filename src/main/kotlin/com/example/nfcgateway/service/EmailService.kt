package com.example.nfcgateway.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    fun sendCredentialsEmail(toEmail: String, password: String, employeeId: String, name: String) {
        val message = SimpleMailMessage().apply {
            setTo(toEmail)
            subject = "Your NFC Gateway Login Credentials"
            text = """
                Dear  ${name},

                Welcome to the NFC Gateway System! Below are your login credentials and employee details:
                
                🔹 Employee ID: $employeeId
                🔹 Email: $toEmail
                🔹 Password: $password
                
                Please use these credentials to log in to the system. For security reasons, we recommend changing your password after your first login.
                
                To mark your attendance using NFC, simply tap your registered NFC-enabled device at the designated terminal.
                
                If you encounter any issues, feel free to contact IT support.
                
                Best regards,
                NFC-Gateway
                kalkalneeraj1@gmail.com
                
            """.trimIndent()
        }
        try {
            mailSender.send(message)
        }catch (e: Exception){
            throw RuntimeException("Something went wrong : ${e.message}")
        }
    }
}