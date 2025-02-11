package com.example.nfcgateway.util

import com.example.nfcgateway.config.JwtConfig
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
//    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8))

    fun generateToken(userDetails: UserDetails): String {
        return Jwts.builder()
            .setSubject(userDetails.username)
            .claim("roles", userDetails.authorities)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtConfig.expirationsMs))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun extractToken(token:String): String {
        return extractClaims(token).subject
    }

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

    private fun isTokenExpired(token: String): Boolean {
        return extractClaims(token).expiration.before(Date())
    }

}