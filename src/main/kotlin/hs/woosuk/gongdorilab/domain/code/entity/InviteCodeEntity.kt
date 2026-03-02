package hs.woosuk.gongdorilab.domain.code.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "invite_codes")
class InviteCodeEntity(

    @Id
    val code: String, // UUID

    val expiredAt: LocalDateTime,

    var used: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now()
)