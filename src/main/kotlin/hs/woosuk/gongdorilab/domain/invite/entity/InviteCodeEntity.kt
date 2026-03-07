package hs.woosuk.gongdorilab.domain.invite.entity

import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "invite")
class InviteCodeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val code: String,

    @Enumerated(EnumType.STRING)
    val role: MemberRole,

    val expiredAt: LocalDateTime
)