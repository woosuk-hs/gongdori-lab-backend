package hs.woosuk.gongdorilab.domain.jwt.entity

import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import jakarta.persistence.*

@Entity(name = "jwt_refresh")
class TokenEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    val member: MemberEntity,

    var refreshToken: String,

) {
    fun updateToken(newRefreshToken: String) {
        this.refreshToken = newRefreshToken
    }
}