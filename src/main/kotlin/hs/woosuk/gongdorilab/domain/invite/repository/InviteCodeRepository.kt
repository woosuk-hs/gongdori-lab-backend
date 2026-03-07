package hs.woosuk.gongdorilab.domain.invite.repository

import hs.woosuk.gongdorilab.domain.invite.entity.InviteCodeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InviteCodeRepository : JpaRepository<InviteCodeEntity, Long> {
    fun findByCode(code: String): InviteCodeEntity?
}