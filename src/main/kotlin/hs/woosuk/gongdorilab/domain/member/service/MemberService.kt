package hs.woosuk.gongdorilab.domain.member.service

import hs.woosuk.gongdorilab.domain.member.dto.MemberRequestDTO
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val repository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun exists(id: Long): Boolean {
        return repository.existsById(id)
    }

    fun login(dto: MemberRequestDTO) {
        val entity = MemberEntity.create(
            dto.username,
            passwordEncoder.encode(dto.password)!!
        )

        repository.save(entity)
    }

}