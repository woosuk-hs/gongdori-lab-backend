package hs.woosuk.gongdorilab.domain.member.service

import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberResponseDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberUpdateDTO
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import hs.woosuk.gongdorilab.domain.member.mapping.toEntity
import hs.woosuk.gongdorilab.domain.member.mapping.toResponseDTO
import hs.woosuk.gongdorilab.domain.member.repository.MemberRepository
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    fun findAll(): List<MemberResponseDTO> =
        memberRepository.findAll().map { it.toResponseDTO() }

    fun findById(id: Long): MemberResponseDTO? =
        memberRepository.findById(id).orElse(null)?.toResponseDTO()

    fun findByUsername(username: String): MemberEntity? =
        memberRepository.findByUsername(username)

    @Transactional
    fun createMember(dto: MemberCreateDTO): Long {
        if (memberRepository.findByUsername(dto.username) != null)
            throw IllegalArgumentException("Member already exists")

        val entity = dto.toEntity(
            passwordEncoder.encode(dto.password)!!,
            MemberRole.MEMBER
        )
        return memberRepository.save(entity).id!!
    }

    @Transactional
    fun updateMember(id: Long, dto: MemberUpdateDTO): MemberResponseDTO {
        val member = memberRepository.findById(id)
            .orElseThrow { NoSuchElementException("Member not found") }

        dto.password?.let { member.password = passwordEncoder.encode(it)!! }
        dto.name?.let { member.name = it }
        dto.role?.let { member.role = it }

        return memberRepository.save(member).toResponseDTO()
    }

    @Transactional
    fun deleteMember(id: Long) {
        val member = memberRepository.findById(id)
            .orElseThrow { NoSuchElementException("Member not found") }
        memberRepository.delete(member)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val entity = memberRepository.findByUsername(username)
            ?: throw IllegalStateException("User not found")
        return MemberDetails(entity)
    }
}