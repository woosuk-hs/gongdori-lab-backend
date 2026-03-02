package hs.woosuk.gongdorilab.domain.member.repository

import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun findByUsername(username: String): MemberEntity?
}