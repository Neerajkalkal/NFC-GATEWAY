package com.example.nfcgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtConfig(
    val secretKey: String = "4b4777ffbdb6db25ffd034537261e17a5c01fbaeeec920b10e37e104fb975a7e",
    var expirationMs: Long = 0
)
