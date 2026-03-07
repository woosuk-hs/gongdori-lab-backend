package hs.woosuk.gongdorilab.domain.member.dto

import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import hs.woosuk.gongdorilab.domain.member.entity.MemberType
import java.time.LocalDateTime

data class MemberResponseDTO(
    val id: Long,
    val username: String,
    val studentId: String? = null,
    val name: String,
    val role: MemberRole,
    val type: MemberType,
    val github: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)