package hs.woosuk.gongdorilab.domain.member.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "members")
@EntityListeners(AuditingEntityListener::class)
class MemberEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true)
    val username: String,

    var password: String,

    val studentNumber: String?,

    var name: String,

    @Enumerated(EnumType.STRING)
    var role: MemberRole,

    @Enumerated(EnumType.STRING)
    var type: MemberType,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime,

    @LastModifiedDate
    var updatedAt: LocalDateTime

)