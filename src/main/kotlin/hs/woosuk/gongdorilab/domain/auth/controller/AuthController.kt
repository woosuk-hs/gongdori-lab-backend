package hs.woosuk.gongdorilab.domain.auth.controller

import hs.woosuk.gongdorilab.domain.auth.service.AuthService
import hs.woosuk.gongdorilab.domain.jwt.dto.TokenDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberLoginDTO
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

//    @PostMapping("/login")
//    fun login(@RequestBody request: MemberLoginDTO): TokenDTO {
//        val tokens = authService.login(request.username, request.password)
//        return TokenDTO(tokens.access, tokens.refresh)
//    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: MemberLoginDTO,
        response: HttpServletResponse,
        @RequestParam("rememberMe", defaultValue = "false") rememberMe: Boolean
    ): TokenDTO {
        val tokens = authService.login(request.username, request.password, rememberMe)
        setRefreshTokenCookie(response, tokens.refresh, rememberMe)
        return TokenDTO(tokens.access, "")
    }

//    @PostMapping("/logout")
//    fun logout(@AuthenticationPrincipal member: MemberDetails) {
//        authService.logout(member.username)
//    }
    @PostMapping("/logout")
    fun logout(@AuthenticationPrincipal member: MemberDetails, response: HttpServletResponse) {
        authService.logout(member.username)
        clearRefreshTokenCookie(response)
    }

//    @PostMapping("/refresh")
//    fun refresh(@RequestHeader("Authorization") refreshToken: String): TokenDTO {
//        val token = refreshToken.removePrefix("Bearer ")
//        val tokens = authService.refresh(token)
//        return TokenDTO(tokens.access, tokens.refresh)
//    }
    @PostMapping("/refresh")
    fun refresh(request: HttpServletRequest, response: HttpServletResponse): TokenDTO {
        val refreshToken = request.cookies?.firstOrNull { it.name == "refreshToken" }?.value
            ?: throw IllegalArgumentException("Refresh token not found")
        val tokens = authService.refresh(refreshToken)
        setRefreshTokenCookie(response, tokens.refresh, false)
        return TokenDTO(tokens.access, "")
    }

    private fun setRefreshTokenCookie(response: HttpServletResponse, token: String, rememberMe: Boolean) {
        val cookie = Cookie("refreshToken", token).apply {
            isHttpOnly = true
            path = "/"
            maxAge = if (rememberMe) 60 * 60 * 24 * 30 else 60 * 60 * 8
        }
        response.addCookie(cookie)
    }

    private fun clearRefreshTokenCookie(response: HttpServletResponse) {
        val cookie = Cookie("refreshToken", null).apply {
            path = "/"
            maxAge = 0
        }
        response.addCookie(cookie)
    }
}