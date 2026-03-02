package hs.woosuk.gongdorilab.domain.auth.controller

import hs.woosuk.gongdorilab.domain.auth.service.AuthService
import hs.woosuk.gongdorilab.domain.jwt.dto.TokenDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberLoginDTO
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: MemberLoginDTO): TokenDTO {
        val tokens = authService.login(request.username, request.password)
        return TokenDTO(tokens.access, tokens.refresh)
    }

    @PostMapping("/logout")
    fun logout(@AuthenticationPrincipal member: MemberDetails) {
        authService.logout(member.username)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestHeader("Authorization") refreshToken: String): TokenDTO {
        val token = refreshToken.removePrefix("Bearer ")
        val tokens = authService.refresh(token)
        return TokenDTO(tokens.access, tokens.refresh)
    }
}