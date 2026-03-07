package hs.woosuk.gongdorilab.domain.member.dto

import hs.woosuk.gongdorilab.domain.member.entity.MemberType

data class MemberCreateDTO(
    val username: String,
    val password: String,
    val name: String,
    val studentId: String? = null,
    val type: MemberType = MemberType.STUDENT,
    val github: String? = null,
    val inviteCode: String
)