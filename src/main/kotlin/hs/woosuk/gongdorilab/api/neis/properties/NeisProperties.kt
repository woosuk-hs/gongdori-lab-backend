package hs.woosuk.gongdorilab.api.neis.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "neis")
data class NeisProperties(
    val key: String,
    val baseUri: String = "https://open.neis.go.kr/hub"
)
