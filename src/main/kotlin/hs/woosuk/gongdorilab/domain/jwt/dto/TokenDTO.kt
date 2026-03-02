package hs.woosuk.gongdorilab.domain.jwt.dto

data class TokenDTO(
    val access: String,
    val refresh: String
)