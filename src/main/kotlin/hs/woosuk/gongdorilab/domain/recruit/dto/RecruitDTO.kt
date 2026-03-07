package hs.woosuk.gongdorilab.domain.recruit.dto

import hs.woosuk.gongdorilab.domain.recruit.entity.RecruitStatus

data class RecruitDTO(
    val studentId: String,
    val name: String,
    val languages: List<String>,
    val motivation: String,
    val github: String? = null,
    val status: RecruitStatus = RecruitStatus.PENDING
)