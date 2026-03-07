package hs.woosuk.gongdorilab.domain.recruit.service

import hs.woosuk.gongdorilab.domain.invite.dto.InviteCodeCreateDTO
import hs.woosuk.gongdorilab.domain.invite.service.InviteCodeService
import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import hs.woosuk.gongdorilab.domain.recruit.dto.RecruitDTO
import hs.woosuk.gongdorilab.domain.recruit.dto.RecruitUpdateDTO
import hs.woosuk.gongdorilab.domain.recruit.entity.RecruitEntity
import hs.woosuk.gongdorilab.domain.recruit.entity.RecruitStatus
import hs.woosuk.gongdorilab.domain.recruit.mapper.toEntity
import hs.woosuk.gongdorilab.domain.recruit.repository.RecruitRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RecruitService(
    private val recruitRepository: RecruitRepository,
    private val inviteCodeService: InviteCodeService
) {

    @Transactional
    fun join(dto: RecruitDTO): RecruitDTO {
        if (recruitRepository.findByStudentId(dto.studentId) != null)
            throw IllegalArgumentException("JoinForm already exists")
        recruitRepository.save(dto.toEntity())
        return dto
    }

    fun findById(id: Long): RecruitEntity =
        recruitRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("No recruit found with id = $id")

    fun findByStudentId(studentId: String): RecruitEntity =
        recruitRepository.findByStudentId(studentId)
            ?: throw IllegalArgumentException("No recruit found with studentId = $studentId")

    fun findAll(): List<RecruitEntity> =
        recruitRepository.findAll()

    @Transactional
    fun updateByStudentId(studentId: String, dto: RecruitUpdateDTO): RecruitEntity {
        val recruit = recruitRepository.findByStudentId(studentId)
            ?: throw IllegalArgumentException("No recruit found with studentId = $studentId")

        val prev = recruit.status
        val next = dto.status

        if (prev == next) return recruit

        when (prev to next) {
            RecruitStatus.PENDING to RecruitStatus.ACCEPTED -> {
                val invite = inviteCodeService.createInviteCode(InviteCodeCreateDTO(role = MemberRole.MEMBER))
                recruit.inviteCode = invite.code
            }

            RecruitStatus.ACCEPTED to RecruitStatus.PENDING,
            RecruitStatus.ACCEPTED to RecruitStatus.REJECTED -> {
                recruit.inviteCode?.let { inviteCodeService.deleteInviteCode(it) }
                recruit.inviteCode = null
            }
        }

        recruit.status = next
        return recruit
    }
}