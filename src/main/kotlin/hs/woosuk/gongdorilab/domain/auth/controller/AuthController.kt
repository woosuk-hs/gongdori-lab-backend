package hs.woosuk.gongdorilab.domain.auth.controller

import hs.woosuk.gongdorilab.domain.auth.service.AuthService
import hs.woosuk.gongdorilab.domain.invite.service.InviteCodeService
import hs.woosuk.gongdorilab.domain.jwt.dto.TokenDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberLoginDTO
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val inviteCodeService: InviteCodeService
) {

    @PostMapping("/login")
    fun login(@RequestBody dto: MemberLoginDTO): TokenDTO {
        return authService.login(dto)
    }

    @PostMapping("/logout")
    fun logout(@AuthenticationPrincipal member: MemberDetails) {
        authService.logout(member.member)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshToken: String): TokenDTO {
        return authService.refresh(refreshToken)
    }

    @PostMapping("/join")
    fun join(@RequestBody dto: MemberCreateDTO): Long {
        return authService.join(dto)
    }
}