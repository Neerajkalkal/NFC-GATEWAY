package com.example.nfcgateway.util

import com.example.nfcgateway.config.JwtConfig
import com.example.nfcgateway.model.Employee
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JWTService(
    private val jwtConfig: JwtConfig,
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtConfig.secretKey.toByteArray())

    fun generateToken(employee: Employee): String {
        return Jwts.builder()
            .setSubject(employee.email)
            .claim("roles", listOf(if (employee.isAdmin) "ROLE_ADMIN" else "ROLE_EMPLOYEE"),)
            .claim("email", employee.email)
            .claim("name", employee.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtConfig.expirationMs))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun extractUsername(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        }catch (e:Exception){
            false
        }
    }

//    fun extractToken(token:String): String {
//        return extractClaims(token).subject
//    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun extractClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

//    fun extractUsername(token: String): String {
//        return extractClaims(token).subject
//    }
    private fun isTokenExpired(token: String): Boolean {
        return extractClaims(token).expiration.before(Date())
    }

}