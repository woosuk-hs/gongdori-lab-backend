package hs.woosuk.gongdorilab.domain.member.mapping

import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberResponseDTO
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import java.time.LocalDateTime

//fun MemberEntity.toResponseDTO(): MemberResponseDTO {
//    return MemberResponseDTO(
//        id = this.id!!,
//        username = this.username,
//        role = this.role,
//        type = this.type,
//    )
//}

fun MemberEntity.toResponseDTO(): MemberResponseDTO = MemberResponseDTO(
    id = this.id ?: 0,
    username = this.username,
    studentNumber = this.studentNumber,
    name = this.name,
    role = this.role,
    type = this.type,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun MemberCreateDTO.toEntity(
    encodedPassword: String,
    role: MemberRole
): MemberEntity = MemberEntity(
    username = this.username,
    password = encodedPassword,
    studentNumber = this.studentNumber,
    name = this.name,
    role = role,
    type = this.type,
    createdAt = LocalDateTime.now(),
    updatedAt = LocalDateTime.now()
)
