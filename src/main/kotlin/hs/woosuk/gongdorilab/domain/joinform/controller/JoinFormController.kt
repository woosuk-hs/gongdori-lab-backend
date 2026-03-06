package hs.woosuk.gongdorilab.domain.joinform.controller

import hs.woosuk.gongdorilab.domain.joinform.dto.JoinFormDTO
import hs.woosuk.gongdorilab.domain.joinform.dto.JoinFormResponseDTO
import hs.woosuk.gongdorilab.domain.joinform.dto.JoinFormUpdateDTO
import hs.woosuk.gongdorilab.domain.joinform.service.JoinFormService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/join-form")
class JoinFormController(
    private val joinFormService: JoinFormService
) {

    @PostMapping
    fun join(@RequestBody dto: JoinFormDTO): ResponseEntity<JoinFormDTO> {
        val entity = joinFormService.join(dto)
        return ResponseEntity.ok(entity)
    }

    @GetMapping
    fun index(): List<JoinFormResponseDTO> =
        joinFormService.findAll()

    @GetMapping("/{studentId}")
    fun getById(@PathVariable studentId: String): JoinFormResponseDTO? =
        joinFormService.findByStudentId(studentId)

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @RequestBody dto: JoinFormUpdateDTO
    ): JoinFormResponseDTO =
        joinFormService.update(id, dto)

}