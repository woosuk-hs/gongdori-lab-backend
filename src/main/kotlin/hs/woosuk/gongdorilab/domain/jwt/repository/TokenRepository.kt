package hs.woosuk.gongdorilab.domain.jwt.repository

import hs.woosuk.gongdorilab.domain.jwt.entity.TokenEntity
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<TokenEntity, Long> {
    fun findByMember(member: MemberEntity): TokenEntity?
    fun findByRefreshToken(refresh: String): TokenEntity?
}