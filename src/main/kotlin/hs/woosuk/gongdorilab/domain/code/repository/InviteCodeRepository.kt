package hs.woosuk.gongdorilab.domain.code.repository

import hs.woosuk.gongdorilab.domain.code.entity.InviteCodeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InviteCodeRepository : JpaRepository<InviteCodeEntity, String>