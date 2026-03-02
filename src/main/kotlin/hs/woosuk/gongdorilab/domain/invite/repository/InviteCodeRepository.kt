package hs.woosuk.gongdorilab.domain.invite.repository

import hs.woosuk.gongdorilab.domain.invite.entity.InviteCodeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InviteCodeRepository : JpaRepository<InviteCodeEntity, String> {
    fun findByCode(code: String): InviteCodeEntity?
}