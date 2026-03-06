package hs.woosuk.gongdorilab.domain.joinform.dto

import hs.woosuk.gongdorilab.domain.joinform.entity.JoinFormStatus

data class JoinFormDTO(
    val studentId: String,
    val name: String,
    val languages: List<String>,
    val motivation: String,
    val github: String? = null,
    val joinFormStatus: JoinFormStatus = JoinFormStatus.PENDING
)