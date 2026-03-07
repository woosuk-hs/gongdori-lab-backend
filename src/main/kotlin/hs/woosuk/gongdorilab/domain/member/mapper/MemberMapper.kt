package hs.woosuk.gongdorilab.domain.member.mapper

import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberResponseDTO
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import java.time.LocalDateTime

fun MemberEntity.toResponseDTO(): MemberResponseDTO = MemberResponseDTO(
    id = this.id ?: 0,
    username = this.username,
    studentId = this.studentId,
    name = this.name,
    role = this.role,
    type = this.type,
    github = github,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

//fun MemberCreateDTO.toEntity(
//    encodedPassword: String,
//    studentId: String?,
//    role: MemberRole,
//    type: MemberType
//): MemberEntity = MemberEntity(
//    username = this.username,
//    password = encodedPassword,
//    studentId = studentId ?: this.studentId,
//    name = this.name,
//    role = role,
//    type = type,
//    github = this.github,
//    createdAt = LocalDateTime.now(),
//    updatedAt = LocalDateTime.now()
//)

fun MemberCreateDTO.toEntity(
    encodedPassword: String,
    role: MemberRole
): MemberEntity = MemberEntity(
    username = this.username,
    password = encodedPassword,
    studentId = this.studentId,
    name = this.name,
    role = role,
    type = this.type,
    github = this.github,
    createdAt = LocalDateTime.now(),
    updatedAt = LocalDateTime.now()
)

