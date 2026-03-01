package hs.woosuk.gongdorilab.domain.member.dto

import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.entity.MemberRole

data class MemberResponseDTO(
    val id: Long,
    val username: String,
    val role: MemberRole
) {
    companion object {
        fun from(entity: MemberEntity) =
            MemberResponseDTO(
                id = entity.id!!,
                username = entity.username,
                role = entity.role
            )
    }
}