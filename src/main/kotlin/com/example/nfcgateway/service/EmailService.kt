package com.example.nfcgateway.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    fun sendCredentialsEmail(toEmail: String, password: String) {
        val message = SimpleMailMessage().apply {
            setTo(toEmail)
            subject = "Your NFC Gateway Account Credentials"
            text = """
                Your account has been created!
                Username: $toEmail
                Password: $password
                
                Login here: http://your-nfc-gateway-url/login
            """.trimIndent()
        }
        try {
            mailSender.send(message)
        }catch (e: Exception){
            throw RuntimeException("Something went wrong : ${e.message}")
        }
    }
}