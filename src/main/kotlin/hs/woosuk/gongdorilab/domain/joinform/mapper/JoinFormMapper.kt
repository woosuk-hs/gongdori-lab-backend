package hs.woosuk.gongdorilab.domain.joinform.mapper

import hs.woosuk.gongdorilab.domain.joinform.dto.JoinFormDTO
import hs.woosuk.gongdorilab.domain.joinform.dto.JoinFormResponseDTO
import hs.woosuk.gongdorilab.domain.joinform.entity.JoinFormEntity

fun JoinFormDTO.toEntity(): JoinFormEntity {
    return JoinFormEntity(
        studentId = studentId,
        name = name,
        languages = languages,
        motivation = motivation,
        github = github,
    )
}

fun JoinFormEntity.toResponseDTO(): JoinFormResponseDTO = JoinFormResponseDTO(
    id = this.id ?: 0,
    studentId = this.studentId,
    name = this.name,
    languages = this.languages,
    motivation = this.motivation,
    github = this.github,
    status = this.status,
)
