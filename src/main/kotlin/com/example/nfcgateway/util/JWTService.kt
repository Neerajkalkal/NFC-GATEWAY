package com.example.nfcgateway.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTService {
    private val  secret = System.getenv("SECRET") ?: "mysecretpassword"
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(email: String): String {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 *60 *10))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()
    }
}