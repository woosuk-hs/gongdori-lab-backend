package hs.woosuk.gongdorilab.domain.member.service

import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberResponseDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberUpdateDTO
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import hs.woosuk.gongdorilab.domain.member.mapper.toEntity
import hs.woosuk.gongdorilab.domain.member.mapper.toResponseDTO
import hs.woosuk.gongdorilab.domain.member.repository.MemberRepository
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    fun findAll(): List<MemberEntity> =
        memberRepository.findAll()

    fun findById(id: Long): MemberEntity =
        memberRepository.findByIdOrNull(id)
            ?: throw IllegalStateException("Member not found")

    fun findByUsername(username: String): MemberEntity =
        memberRepository.findByUsername(username)
            ?: throw IllegalArgumentException("Username $username not found")

    fun findByStudentId(studentId: String): MemberEntity =
        memberRepository.findByStudentId(studentId)
            ?: throw IllegalArgumentException("Student $studentId not found")

//    @Transactional
//    fun create(dto: MemberCreateDTO, studentId: String?, role: MemberRole, type: MemberType): Long {
//        if (memberRepository.findByUsername(dto.username) != null)
//            throw IllegalArgumentException("Username already exists")
//
//        val entity = dto.toEntity(
//            passwordEncoder.encode(dto.password)!!,
//            studentId,
//            role,
//            type
//        )
//        return memberRepository.save(entity).id!!
//    }

    @Transactional
    fun create(dto: MemberCreateDTO, memberRole: MemberRole): Long {
        if (memberRepository.findByUsername(dto.username) != null)
            throw IllegalArgumentException("Username already exists")

        val entity = dto.toEntity(
            passwordEncoder.encode(dto.password)!!,
            memberRole
        )
        return memberRepository.save(entity).id!!
    }

    @Transactional
    fun updateMember(id: Long, dto: MemberUpdateDTO): MemberResponseDTO {
        val member = findById(id)

        dto.username?.let { member.username = it }
        dto.password?.let { member.password = passwordEncoder.encode(it)!! }
        dto.github?.let { member.github = it }
//        dto.name?.let { member.name = it }
//        dto.role?.let { member.role = it }

        return memberRepository.save(member).toResponseDTO()
    }

    @Transactional
    fun deleteMember(id: Long) {
        val member = findById(id)
        memberRepository.delete(member)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val entity = memberRepository.findByUsername(username)
            ?: throw IllegalStateException("Member not found")
        return MemberDetails(entity)
    }
}