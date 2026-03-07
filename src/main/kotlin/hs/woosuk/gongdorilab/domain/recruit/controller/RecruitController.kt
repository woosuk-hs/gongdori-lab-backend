package hs.woosuk.gongdorilab.domain.recruit.controller

import hs.woosuk.gongdorilab.domain.recruit.dto.RecruitDTO
import hs.woosuk.gongdorilab.domain.recruit.dto.RecruitResponseDTO
import hs.woosuk.gongdorilab.domain.recruit.dto.RecruitUpdateDTO
import hs.woosuk.gongdorilab.domain.recruit.mapper.toResponseDTO
import hs.woosuk.gongdorilab.domain.recruit.service.RecruitService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/recruit")
class RecruitController(
    private val recruitService: RecruitService
) {

    @PostMapping
    fun join(@RequestBody dto: RecruitDTO): ResponseEntity<RecruitDTO> {
        val entity = recruitService.join(dto)
        return ResponseEntity.ok(entity)
    }

    @GetMapping("/{studentId}")
    fun getById(@PathVariable studentId: String): RecruitResponseDTO =
        recruitService.findByStudentId(studentId).toResponseDTO()

    @GetMapping
    fun index(): List<RecruitResponseDTO> =
        recruitService.findAll().map { it.toResponseDTO() }

    @PatchMapping("/status{studentId}")
    fun updateStatusByStudentId(
        @PathVariable studentId: String,
        @RequestBody dto: RecruitUpdateDTO
    ): RecruitResponseDTO =
        recruitService.updateByStudentId(studentId, dto).toResponseDTO()
}