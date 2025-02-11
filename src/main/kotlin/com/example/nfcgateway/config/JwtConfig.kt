package com.example.nfcgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtConfig(
    var secretKey: String = "",
    var expirationsMs: Long = 0,
)
