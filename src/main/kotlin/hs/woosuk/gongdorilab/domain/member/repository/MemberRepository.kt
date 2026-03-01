package hs.woosuk.gongdorilab.domain.member.repository

import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<MemberEntity, Long>