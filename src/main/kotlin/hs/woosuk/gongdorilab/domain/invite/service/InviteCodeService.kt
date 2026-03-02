package hs.woosuk.gongdorilab.domain.invite.service

import hs.woosuk.gongdorilab.domain.invite.dto.InviteCodeCreateDTO
import hs.woosuk.gongdorilab.domain.invite.dto.InviteCodeResponseDTO
import hs.woosuk.gongdorilab.domain.invite.entity.InviteCodeEntity
import hs.woosuk.gongdorilab.domain.invite.repository.InviteCodeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class InviteCodeService(
    private val inviteCodeRepository: InviteCodeRepository
) {

    fun createInviteCode(dto: InviteCodeCreateDTO): InviteCodeResponseDTO {
        val code = UUID.randomUUID().toString().replace("-", "").substring(0, 12)
        val expiredAt = LocalDateTime.now().plusHours(dto.validHours)

        val entity = InviteCodeEntity(
            code = code,
            role = dto.role,
            expiredAt = expiredAt
        )

        val saved = inviteCodeRepository.save(entity)
        return InviteCodeResponseDTO(
            id = saved.id!!,
            code = saved.code,
            role = saved.role,
            expiredAt = saved.expiredAt
        )
    }

    fun validateInviteCode(code: String): InviteCodeEntity {
        val invite = inviteCodeRepository.findByCode(code)
            ?: throw IllegalArgumentException("Invite code not found")
        if (invite.expiredAt.isBefore(LocalDateTime.now())) {
            inviteCodeRepository.delete(invite)
            throw IllegalArgumentException("Invite code expired")
        }
        return invite
    }

    fun deleteInviteCode(code: String) {
        val invite = inviteCodeRepository.findByCode(code)
            ?: throw IllegalArgumentException("Invite code not found")
        inviteCodeRepository.delete(invite)
    }
}