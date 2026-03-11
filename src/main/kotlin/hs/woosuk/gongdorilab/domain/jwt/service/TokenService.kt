package hs.woosuk.gongdorilab.domain.jwt.service

import hs.woosuk.gongdorilab.domain.jwt.dto.TokenDTO
import hs.woosuk.gongdorilab.domain.jwt.entity.TokenEntity
import hs.woosuk.gongdorilab.domain.jwt.properties.JwtProperties
import hs.woosuk.gongdorilab.domain.jwt.repository.TokenRepository
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.repository.MemberRepository
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Service
@Transactional(readOnly = true)
class TokenService(
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository,
    private val jwtProperties: JwtProperties
) {

    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun init() {
        secretKey = SecretKeySpec(
            jwtProperties.key.toByteArray(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().algorithm
        )
    }

    private fun createAccessToken(member: MemberEntity): String =
        Jwts.builder()
            .subject(member.username)
            .claim("id", member.id)
            .claim("role", member.role.name)
            .expiration(Date(System.currentTimeMillis() + jwtProperties.access))
            .signWith(secretKey)
            .compact()

    private fun createRefreshToken(member: MemberEntity, rememberMe: Boolean): String {
        val validTime =
            if (rememberMe) 1000L * 60 * 60 * 24 * 30
            else jwtProperties.refresh

        return Jwts.builder()
            .subject(member.username)
            .expiration(Date(System.currentTimeMillis() + validTime))
            .signWith(secretKey)
            .compact()
    }

    @Transactional
    fun generateTokens(member: MemberEntity, rememberMe: Boolean = false): TokenDTO {
        val accessToken = createAccessToken(member)
        val refreshToken = createRefreshToken(member, rememberMe)

        saveOrUpdateRefreshToken(member, refreshToken)

        return TokenDTO(
            access = accessToken,
            refresh = refreshToken
        )
    }

    fun findTokenByMember(member: MemberEntity): TokenEntity? =
        tokenRepository.findByMember(member)

    fun findTokenByRefreshToken(refreshToken: String): TokenEntity? =
        tokenRepository.findByRefreshToken(refreshToken)

    @Transactional
    fun saveOrUpdateRefreshToken(member: MemberEntity, refreshToken: String) {
        val token = findTokenByMember(member)

        if (token == null) {
            tokenRepository.save(
                TokenEntity(
                    member = member,
                    refreshToken = refreshToken
                )
            )
        } else {
            token.updateToken(refreshToken)
        }
    }

    @Transactional
    fun deleteToken(member: MemberEntity) {
        findTokenByMember(member)?.let {
            tokenRepository.delete(it)
        }
    }

    fun validateToken(token: String): Boolean =
        try {
            parseToken(token)
            true
        } catch (e: Exception) {
            false
        }

    private fun parseToken(token: String): Claims =
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload

    fun getUsername(token: String): String =
        parseToken(token).subject

    fun getRole(token: String): String =
        parseToken(token).get("role", String::class.java)

    fun getAuthentication(token: String): Authentication {

        val username = getUsername(token)

        val member = memberRepository.findByUsername(username)
            ?: throw IllegalArgumentException("Member not found")

        val memberDetails = MemberDetails(member)

        return UsernamePasswordAuthenticationToken(
            memberDetails,
            null,
            memberDetails.authorities
        )
    }
}