package hs.woosuk.gongdorilab.domain.recruit.dto

import hs.woosuk.gongdorilab.domain.recruit.entity.RecruitStatus

data class RecruitUpdateDTO(
    val status: RecruitStatus
)