package hs.woosuk.gongdorilab.domain.recruit.mapper

import hs.woosuk.gongdorilab.domain.recruit.dto.RecruitDTO
import hs.woosuk.gongdorilab.domain.recruit.dto.RecruitResponseDTO
import hs.woosuk.gongdorilab.domain.recruit.entity.RecruitEntity

fun RecruitDTO.toEntity(): RecruitEntity {
    return RecruitEntity(
        studentId = studentId,
        name = name,
        languages = languages,
        motivation = motivation,
        github = github,
    )
}

fun RecruitEntity.toResponseDTO(): RecruitResponseDTO = RecruitResponseDTO(
    id = this.id ?: 0,
    studentId = this.studentId,
    name = this.name,
    languages = this.languages,
    motivation = this.motivation,
    github = this.github,
    status = this.status,
    inviteCode = this.inviteCode,
)