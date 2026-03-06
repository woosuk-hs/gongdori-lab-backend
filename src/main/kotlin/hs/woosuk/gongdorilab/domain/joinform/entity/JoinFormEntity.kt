package hs.woosuk.gongdorilab.domain.joinform.entity

import jakarta.persistence.*

@Entity(name = "join_form")
class JoinFormEntity(

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
    var status: JoinFormStatus = JoinFormStatus.PENDING
) {
    fun updateStatus(status: JoinFormStatus) {
        this.status = status
    }
}