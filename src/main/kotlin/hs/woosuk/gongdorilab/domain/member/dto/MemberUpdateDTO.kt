package hs.woosuk.gongdorilab.domain.member.dto

data class MemberUpdateDTO(
    val username: String? = null,
    val password: String? = null,
    val github: String? = null,
//    val name: String? = null,
//    val role: MemberRole? = null,
//    val type: MemberType? = null,
)
