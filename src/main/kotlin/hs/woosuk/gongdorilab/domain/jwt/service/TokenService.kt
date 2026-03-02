package hs.woosuk.gongdorilab.domain.jwt.service

import hs.woosuk.gongdorilab.domain.jwt.dto.TokenDTO
import hs.woosuk.gongdorilab.domain.jwt.entity.TokenEntity
import hs.woosuk.gongdorilab.domain.jwt.repository.TokenRepository
import hs.woosuk.gongdorilab.domain.member.entity.MemberEntity
import hs.woosuk.gongdorilab.domain.member.repository.MemberRepository
import hs.woosuk.gongdorilab.domain.member.security.MemberDetails
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
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
    private val memberRepository: MemberRepository
) {

    @Value($$"${jwt.key}")
    private lateinit var key: String

    @Value($$"${jwt.access}")
    private var accessValidTime: Int = 86400000

    @Value($$"${jwt.refresh}")
    private var refreshValidTime: Int = 604800000

    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun init() {
        secretKey = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().algorithm)
    }

    fun findTokenByMember(memberEntity: MemberEntity): TokenEntity? =
        tokenRepository.findByMember(memberEntity)

    fun findTokenByUsername(username: String): TokenEntity? {
        val member = memberRepository.findByUsername(username) ?: return null
        return findTokenByMember(member)
    }

    fun findTokenByRefreshToken(refreshToken: String): TokenEntity? =
        tokenRepository.findByRefreshToken(refreshToken)

    private fun createAccessToken(member: MemberEntity): String =
        Jwts.builder()
            .subject(member.username)
//            .claim("username", member.username)
            .claim("role", member.role.name)
            .expiration(Date(System.currentTimeMillis() + accessValidTime))
            .signWith(secretKey)
            .compact()

//    private fun createRefreshToken(): String =
//        Jwts.builder()
//            .expiration(Date(System.currentTimeMillis() + refreshValidTime))
//            .signWith(secretKey)
//            .compact()
    private fun createRefreshToken(rememberMe: Boolean): String {
        val validTime = if (rememberMe) 60 * 60 * 24 * 30 * 1000 else refreshValidTime.toLong()
        return Jwts.builder()
            .expiration(Date(System.currentTimeMillis() + validTime))
            .signWith(secretKey)
            .compact()
    }

    @Transactional
    fun saveOrUpdateRefreshToken(member: MemberEntity, refreshToken: String) {
        val tokenEntity = findTokenByMember(member)
        if (tokenEntity == null) {
            tokenRepository.save(TokenEntity(member = member, refreshToken = refreshToken))
        } else {
            tokenEntity.updateToken(refreshToken)
        }
    }

    @Transactional
    fun generateTokens(member: MemberEntity, rememberMe: Boolean = false): TokenDTO {
        val accessToken = createAccessToken(member)
        val refreshToken = createRefreshToken(rememberMe)
        saveOrUpdateRefreshToken(member, refreshToken)
        return TokenDTO(accessToken, refreshToken)
    }
//    fun generateTokens(member: MemberEntity): TokenDTO {
//        val accessToken = createAccessToken(member)
//        val refreshToken = createRefreshToken()
//        saveOrUpdateRefreshToken(member, refreshToken)
//        return TokenDTO(accessToken, refreshToken)
//    }

    fun validateAccessToken(token: String): Boolean {
        return try {
            parseToken(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun parseToken(token: String): Claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .payload

    fun getUsernameFromToken(token: String): String =
        parseToken(token).subject
        // parseToken(token).get("username", String::class.java)

    fun getRoleFromToken(token: String): String =
        parseToken(token).get("role", String::class.java)

    fun refreshTokenExists(refreshToken: String): Boolean =
        findTokenByRefreshToken(refreshToken) != null

    fun getAuthentication(token: String): Authentication {
        val username = getUsernameFromToken(token)
        val memberEntity = memberRepository.findByUsername(username)
            ?: throw IllegalArgumentException("Member not found")
        val memberDetails = MemberDetails(memberEntity)

        return UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.authorities)
    }

    @Transactional
    fun deleteToken(tokenEntity: TokenEntity) {

        tokenRepository.delete(tokenEntity)
    }

}