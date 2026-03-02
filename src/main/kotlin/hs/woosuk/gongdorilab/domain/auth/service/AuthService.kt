package hs.woosuk.gongdorilab.domain.auth.service

import hs.woosuk.gongdorilab.domain.jwt.dto.TokenDTO
import hs.woosuk.gongdorilab.domain.jwt.service.TokenService
import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
import hs.woosuk.gongdorilab.domain.member.service.MemberService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val memberService: MemberService,
    private val tokenService: TokenService,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(username: String, password: String): TokenDTO {
        val member = memberService.findByUsername(username)
            ?: throw IllegalArgumentException("Member not found")

        if (!passwordEncoder.matches(password, member.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        return tokenService.generateTokens(member)
    }

    fun logout(username: String) {
        val member = memberService.findByUsername(username)
            ?: throw IllegalArgumentException("Member not found")
        tokenService.findTokenByMember(member)?.let {
            tokenService.deleteToken(it)
        }
    }

    fun refresh(refreshToken: String): TokenDTO {
        if (!tokenService.refreshTokenExists(refreshToken)) {
            throw IllegalArgumentException("Invalid refresh token")
        }

        val member = tokenService.findTokenByRefreshToken(refreshToken)!!.member
        return tokenService.generateTokens(member)
    }

    @Transactional
    fun join(dto: MemberCreateDTO): Long {
//        val invite = inviteCodeRepository.findByCode(inviteCode)
//            ?: throw IllegalArgumentException("Invalid invite code")
//
//        if (invite.expiredAt.isBefore(LocalDateTime.now())) {
//            throw IllegalArgumentException("Invite code expired")
//        }
        val memberId = memberService.createMember(dto)
        // inviteCodeRepository.delete(invite)

        return memberId
    }
}