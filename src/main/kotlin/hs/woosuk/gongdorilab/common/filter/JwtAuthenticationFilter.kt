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

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        println("========== JWT FILTER START ==========")
        println("Request URI: ${request.requestURI}")

        val bearerToken = request.getHeader("Authorization")
        println("Authorization Header: $bearerToken")

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

            val accessToken = bearerToken.substring(7)
            println("Extracted AccessToken: $accessToken")

            val isValid = tokenService.validateAccessToken(accessToken)
            println("Token Valid: $isValid")

            if (isValid) {
                val authentication = tokenService.getAuthentication(accessToken)
                println("Authentication Object: $authentication")

                SecurityContextHolder.getContext().authentication = authentication
                println("SecurityContext Updated")
            } else {
                println("Token validation failed")
            }

        } else {
            println("No Bearer token found")
        }

        println("Current SecurityContext: ${SecurityContextHolder.getContext().authentication}")
        println("========== JWT FILTER END ==========")

        filterChain.doFilter(request, response)
    }
}