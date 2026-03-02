package hs.woosuk.gongdorilab.domain.auth.service

import hs.woosuk.gongdorilab.domain.jwt.dto.TokenDTO
import hs.woosuk.gongdorilab.domain.jwt.service.TokenService
import hs.woosuk.gongdorilab.domain.member.dto.MemberCreateDTO
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
    private val passwordEncoder: PasswordEncoder
) {

//    fun login(username: String, password: String): TokenDTO {
//        val member = memberService.findByUsername(username)
//            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "멤버가 존재하지 않습니다.")
//
//        if (!passwordEncoder.matches(password, member.password)) {
//            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.")
//        }
//
//        return tokenService.generateTokens(member)
//    }
    fun login(username: String, password: String, rememberMe: Boolean): TokenDTO {
        val member = memberService.findByUsername(username)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "멤버가 존재하지 않습니다.")

        if (!passwordEncoder.matches(password, member.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.")
        }

        return tokenService.generateTokens(member, rememberMe)
    }

    fun logout(username: String) {
        val member = memberService.findByUsername(username)
            ?: throw IllegalArgumentException("멤버가 존재하지 않습니다.")
        tokenService.findTokenByMember(member)?.let {
            tokenService.deleteToken(it)
        }
    }

    fun refresh(refreshToken: String): TokenDTO {
        if (!tokenService.refreshTokenExists(refreshToken)) {
            throw IllegalArgumentException("유효하지 않는 refresh token.")
        }

        val member = tokenService.findTokenByRefreshToken(refreshToken)!!.member
        return tokenService.generateTokens(member)
    }

    @Transactional
    fun join(dto: MemberCreateDTO): Long {
//        val invite = inviteCodeRepository.findByCode(inviteCode)
//            ?: throw IllegalArgumentException("유효하지 않는 초대 코드")
//
//        if (invite.expiredAt.isBefore(LocalDateTime.now())) {
//            throw IllegalArgumentException("초대 코드 만료")
//        }
        val memberId = memberService.createMember(dto)
        // inviteCodeRepository.delete(invite)

        return memberId
    }
}