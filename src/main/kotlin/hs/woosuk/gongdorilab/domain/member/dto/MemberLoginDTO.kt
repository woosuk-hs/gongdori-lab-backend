package hs.woosuk.gongdorilab.domain.member.dto

data class MemberLoginDTO(
    val username: String,
    val password: String,
    val rememberMe: Boolean
)