package hs.woosuk.gongdorilab.domain.member.controller

import hs.woosuk.gongdorilab.domain.member.dto.MemberResponseDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberUpdateDTO
import hs.woosuk.gongdorilab.domain.member.mapper.toResponseDTO
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import hs.woosuk.gongdorilab.domain.member.service.MemberService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController (
    private val memberService: MemberService
) {

    @GetMapping
    fun getMembers(): List<MemberResponseDTO> {
        return memberService.findAll().map { it.toResponseDTO() }
    }

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal member: MemberDetails): MemberResponseDTO {
        return member.member.toResponseDTO()
    }

    @GetMapping("/{id}")
    fun getMember(@PathVariable id: Long): MemberResponseDTO {
        return memberService.findById(id).toResponseDTO()
    }

    @GetMapping("/student/{studentId}")
    fun getMemberByStudentId(@PathVariable studentId: String): MemberResponseDTO {
        return memberService.findByStudentId(studentId).toResponseDTO()
    }

    @PatchMapping("/{id}")
    fun updateMember(@PathVariable id: Long, @RequestBody dto: MemberUpdateDTO): MemberResponseDTO {
        return memberService.updateMember(id, dto)
    }

    @PatchMapping("/me")
    fun updateMe(@AuthenticationPrincipal member: MemberDetails, @RequestBody dto: MemberUpdateDTO): MemberResponseDTO {
        return memberService.updateMember(member.member.id!!, dto)
    }

    @DeleteMapping("/{id}")
    fun deleteMember(@PathVariable id: Long) {
        memberService.deleteMember(id)
    }

}