package hs.woosuk.gongdorilab.domain.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val key: String,
    val access: Long = 86400000,
    val refresh: Long = 604800000
)