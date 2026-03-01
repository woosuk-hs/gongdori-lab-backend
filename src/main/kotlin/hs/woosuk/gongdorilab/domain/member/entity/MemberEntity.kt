package hs.woosuk.gongdorilab.domain.member.entity

import jakarta.persistence.*

@Entity
class MemberEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true)
    val username: String,
    val password: String,

    @Enumerated(EnumType.STRING)
    val role: MemberRole,

    @Enumerated(EnumType.STRING)
    val position: MemberPosition

) {
    companion object {
        fun create(username: String, password: String): MemberEntity {
            return MemberEntity(
                username = username,
                password = password,
                role = MemberRole.MEMBER,
                position = MemberPosition.STUDENT
            )
        }
    }
}