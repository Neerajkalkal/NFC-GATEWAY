package com.example.nfcgateway.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTService {
    private val secret = System.getenv("SECRET") ?: Base64.getEncoder().encodeToString("myverylongsecuresecretkey123456789".toByteArray())
    private val key = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))


    fun generateToken(email: String): String {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 *60 *10))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()
    }
}