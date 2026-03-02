package hs.woosuk.gongdorilab.common.filter

import hs.woosuk.gongdorilab.domain.jwt.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val tokenService: TokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val bearerToken = request.getHeader("Authorization")

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            val accessToken = bearerToken.substring(7)

            if (tokenService.validateAccessToken(accessToken)) {
                val authentication = tokenService.getAuthentication(accessToken)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }
}