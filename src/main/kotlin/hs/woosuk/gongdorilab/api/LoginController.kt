package hs.woosuk.gongdorilab.api

import hs.woosuk.gongdorilab.domain.member.dto.MemberRequestDTO
import hs.woosuk.gongdorilab.domain.member.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class LoginController(
    private val memberService: MemberService
) {

    @PostMapping("/login")
    fun login(@RequestBody dto: MemberRequestDTO): String { // ResponseEntity<String> {
        // memberService.login(dto)
        return dto.toString() // ResponseEntity.ok().build()
    }

    @GetMapping("/login")
    fun login(): String {
        return "a"
    }

}