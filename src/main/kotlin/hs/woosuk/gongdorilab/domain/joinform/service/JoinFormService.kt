package hs.woosuk.gongdorilab.domain.joinform.service

import hs.woosuk.gongdorilab.domain.joinform.dto.JoinFormDTO
import hs.woosuk.gongdorilab.domain.joinform.dto.JoinFormResponseDTO
import hs.woosuk.gongdorilab.domain.joinform.dto.JoinFormUpdateDTO
import hs.woosuk.gongdorilab.domain.joinform.mapper.toEntity
import hs.woosuk.gongdorilab.domain.joinform.mapper.toResponseDTO
import hs.woosuk.gongdorilab.domain.joinform.repository.JoinFormRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class JoinFormService(
    private val joinFormRepository: JoinFormRepository
) {

    fun join(dto: JoinFormDTO): JoinFormDTO {
        if (joinFormRepository.findByStudentId(dto.studentId) != null)
            throw IllegalArgumentException("JoinForm already exists")

        joinFormRepository.save(dto.toEntity())
        return dto
    }

    fun findById(id: Long): JoinFormResponseDTO? =
        joinFormRepository.findByIdOrNull(id)?.toResponseDTO()

    fun findByStudentId(studentId: String): JoinFormResponseDTO? =
        joinFormRepository.findByStudentId(studentId)?.toResponseDTO()

    fun findAll(): List<JoinFormResponseDTO> =
        joinFormRepository.findAll().map { it.toResponseDTO() }

    fun update(studentId: String, dto: JoinFormUpdateDTO): JoinFormResponseDTO {
        val joinForm = joinFormRepository.findByStudentId(studentId)

            // .findById(id).orElseThrow { IllegalArgumentException("JoinForm not found") }

        joinForm.updateStatus(dto.status)

        return joinFormRepository.save(joinForm).toResponseDTO()
    }
}