package com.example.nfcgateway.service

import org.apache.commons.text.RandomStringGenerator
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class PasswordService {
    fun generatePassword(): String {
        val generator = RandomStringGenerator.builder()
            .withinRange('0'.code,'z'.code)
            .filteredBy(Character::isLetterOrDigit)
            .build()
        return generator.generate(6)
    }
}