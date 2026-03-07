package hs.woosuk.gongdorilab.domain.recruit.dto

import hs.woosuk.gongdorilab.domain.recruit.entity.RecruitStatus

data class RecruitResponseDTO(
    val id: Long,
    val studentId: String,
    val name: String,
    val languages: List<String>,
    val motivation: String,
    val github: String? = null,
    val status: RecruitStatus,
    val inviteCode: String? = null
)