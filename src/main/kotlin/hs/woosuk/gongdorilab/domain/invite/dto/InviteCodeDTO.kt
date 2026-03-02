package hs.woosuk.gongdorilab.domain.invite.dto

import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import java.time.LocalDateTime

data class InviteCodeResponseDTO(
    val id: Long,
    val code: String,
    val role: MemberRole,
    val expiredAt: LocalDateTime
)
data class InviteCodeCreateDTO(
    val role: MemberRole = MemberRole.MEMBER,
    val validHours: Long = 24
)