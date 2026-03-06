package hs.woosuk.gongdorilab.domain.joinform.dto

import hs.woosuk.gongdorilab.domain.joinform.entity.JoinFormStatus

data class JoinFormResponseDTO(
    val id: Long,
    val studentId: String,
    val name: String,
    val languages: List<String>,
    val motivation: String,
    val github: String? = null,
    val status: JoinFormStatus
) {
}