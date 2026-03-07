package hs.woosuk.gongdorilab.domain.invite.dto

import java.time.LocalDateTime

data class InviteCodeResponseDTO(
    val id: Long,
    val code: String,
    val expiredAt: LocalDateTime
)