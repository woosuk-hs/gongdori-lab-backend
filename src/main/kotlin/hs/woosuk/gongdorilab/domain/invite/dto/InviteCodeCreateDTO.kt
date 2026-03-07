package hs.woosuk.gongdorilab.domain.invite.dto

import hs.woosuk.gongdorilab.domain.member.entity.MemberRole

data class InviteCodeCreateDTO(
    val role: MemberRole = MemberRole.MEMBER,
    val validHours: Long = 24
)