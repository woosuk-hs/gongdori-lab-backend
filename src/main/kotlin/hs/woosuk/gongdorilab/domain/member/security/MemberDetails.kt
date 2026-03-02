package hs.woosuk.gongdorilab.domain.member.security

import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MemberDetails(
    private val member: MemberEntity
) : UserDetails {

    val id: Long
        get() = member.id!!

    val name: String
        get() = member.name

    val role
        get() = member.role

    val type
        get() = member.type

    val studentNumber
        get() = member.studentNumber

    val createdAt
        get() = member.createdAt

    val updatedAt
        get() = member.updatedAt

    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_${member.role.name}"))

    override fun getPassword(): String = member.password

    override fun getUsername(): String = member.username

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}