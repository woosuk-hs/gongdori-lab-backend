package hs.woosuk.gongdorilab.domain.invite.mapper

import hs.woosuk.gongdorilab.domain.invite.dto.InviteCodeResponseDTO
import hs.woosuk.gongdorilab.domain.invite.entity.InviteCodeEntity

fun InviteCodeEntity.toResponseDTO(): InviteCodeResponseDTO = InviteCodeResponseDTO(
    id = id ?: 0,
    code = code,
    expiredAt = expiredAt,
)