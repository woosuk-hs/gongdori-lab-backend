package hs.woosuk.gongdorilab.domain.member.dto

import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import hs.woosuk.gongdorilab.domain.member.entity.MemberType

data class MemberUpdateDTO(
    val username: String? = null,
    val password: String? = null,
    val github: String? = null,
    val studentId: String? = null,
    val name: String? = null,
    val role: MemberRole? = null,
    val type: MemberType? = null,
)
