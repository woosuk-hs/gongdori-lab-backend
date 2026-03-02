package hs.woosuk.gongdorilab.common.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets
import java.util.Date

object JWTUtil {

    @Value($$"${jwt.key}")
    private lateinit var secretKeyString: String

    @Value($$"${jwt.access}")
    private var accessTokenExpiresIn: Long = 0

    @Value($$"${jwt.refresh}")
    private var refreshTokenExpiresIn: Long = 0

    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun init() {
        secretKey = SecretKeySpec(secretKeyString.toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().algorithm)
    }

    fun getUsername(token: String): String =
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get("sub", String::class.java)

    fun getRole(token: String): String =
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get("role", String::class.java)

    fun isValid(token: String, isAccess: Boolean): Boolean =
        try {
            val claims: Claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload

            val type = claims["type", String::class.java] ?: return false
            (isAccess && type == "access") || (!isAccess && type == "refresh")
        } catch (e: JwtException) {
            false
        }

    fun createJWT(username: String, role: String, isAccess: Boolean): String {
        val now = System.currentTimeMillis()
        val expiry = if (isAccess) accessTokenExpiresIn else refreshTokenExpiresIn
        val type = if (isAccess) "access" else "refresh"

        return Jwts.builder()
            .claim("sub", username)
            .claim("role", role)
            .claim("type", type)
            .issuedAt(Date(now))
            .expiration(Date(now + expiry))
            .signWith(secretKey)
            .compact()
    }
}