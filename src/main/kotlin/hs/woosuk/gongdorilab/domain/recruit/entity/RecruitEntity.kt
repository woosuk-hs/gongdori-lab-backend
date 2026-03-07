package hs.woosuk.gongdorilab.domain.recruit.entity

import jakarta.persistence.*

@Entity(name = "recruit")
class RecruitEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true)
    val studentId: String,

    val name: String,

    val languages: List<String> = emptyList(),

    val motivation: String,

    val github: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: RecruitStatus = RecruitStatus.PENDING,

    var inviteCode: String? = null
)