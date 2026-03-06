package hs.woosuk.gongdorilab.domain.joinform.dto

import hs.woosuk.gongdorilab.domain.joinform.entity.JoinFormStatus

data class JoinFormUpdateDTO(
    val status: JoinFormStatus
)