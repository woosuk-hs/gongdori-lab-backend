package hs.woosuk.gongdorilab.domain.member.dto

import hs.woosuk.gongdorilab.domain.member.entity.MemberType
import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import java.time.LocalDateTime

data class MemberCreateDTO(
    val username: String,
    val password: String,
    val name: String,
    val studentNumber: String = "",
    val type: MemberType = MemberType.STUDENT,
)

data class MemberRequestDTO(
    val username: String,
    val name: String,
    val studentNumber: String?,
    val role: MemberRole,
    val type: MemberType
)

data class MemberLoginDTO(
    val username: String,
    val password: String
)

data class MemberResponseDTO(
    val id: Long,
    val username: String,
    val studentNumber: String?,
    val name: String,
    val role: MemberRole,
    val type: MemberType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class MemberUpdateDTO(
    val name: String? = null,
    val password: String? = null,
    val role: MemberRole? = null,
    val type: MemberType? = null
)