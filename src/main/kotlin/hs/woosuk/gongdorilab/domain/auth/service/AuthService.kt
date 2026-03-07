package hs.woosuk.gongdorilab.domain.auth.service

import hs.woosuk.gongdorilab.domain.invite.service.InviteCodeService
import hs.woosuk.gongdorilab.domain.jwt.dto.TokenDTO
import hs.woosuk.gongdorilab.domain.jwt.service.TokenService
import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
import hs.woosuk.gongdorilab.domain.member.dto.MemberLoginDTO
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class AuthService(
    private val memberService: MemberService,
    private val tokenService: TokenService,
    private val inviteCodeService: InviteCodeService,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(dto: MemberLoginDTO): TokenDTO {
        val member = memberService.findByUsername(dto.username)

        if (!passwordEncoder.matches(dto.password, member.password))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.")

        return tokenService.generateTokens(member, dto.rememberMe)
    }

    fun logout(member: MemberEntity) {
        tokenService.deleteToken(member)
    }

    fun refresh(refreshToken: String): TokenDTO {
        if (!tokenService.validateToken(refreshToken))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 refresh token")

        val tokenEntity = tokenService.findTokenByRefreshToken(refreshToken)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 refresh token")

        val member = tokenEntity.member
        return tokenService.generateTokens(member)
    }

    @Transactional
    fun join(dto: MemberCreateDTO): Long {
        val invite = inviteCodeService.validateInviteCode(dto.inviteCode)
        val memberId = memberService.create(dto, invite.role)
        inviteCodeService.deleteInviteCode(dto.inviteCode)
        return memberId
    }
}