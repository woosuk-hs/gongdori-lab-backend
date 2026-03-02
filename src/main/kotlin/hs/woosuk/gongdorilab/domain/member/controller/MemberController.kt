package hs.woosuk.gongdorilab.domain.member.controller

import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberResponseDTO
import hs.woosuk.gongdorilab.domain.member.mapping.toResponseDTO
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import hs.woosuk.gongdorilab.domain.member.service.MemberService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController (
    private val memberService: MemberService
) {

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal member: MemberDetails): MemberResponseDTO {
        return MemberResponseDTO(
            id = member.id,
            username = member.username,
            name = member.name,
            role = member.role,
            type = member.type,
            studentNumber = member.studentNumber,
            createdAt = member.createdAt,
            updatedAt = member.updatedAt,
        )
    }

    @PostMapping("/join")
    fun join(@RequestBody memberCreateDTO: MemberCreateDTO): Long {
        return memberService.createMember(memberCreateDTO)
    }

    @GetMapping("/list")
    fun getMembers(): List<MemberResponseDTO> {
        return memberService.findAll()
    }

    @GetMapping("/{username}")
    fun getMemberByUsername(@PathVariable username: String): MemberResponseDTO? {
        return memberService.findByUsername(username)?.toResponseDTO()
    }
}