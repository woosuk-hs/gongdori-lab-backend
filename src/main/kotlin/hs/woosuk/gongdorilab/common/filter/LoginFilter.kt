package hs.woosuk.gongdorilab.common.filter

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.util.StreamUtils
import tools.jackson.module.kotlin.jacksonObjectMapper
import tools.jackson.module.kotlin.readValue
import java.nio.charset.StandardCharsets

class LoginFilter(authenticationManager: AuthenticationManager) :
    AbstractAuthenticationProcessingFilter(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/login")) {

    var usernameParameter: String = "username"
    var passwordParameter: String = "password"

    init {
        this.setAuthenticationManager(authenticationManager)
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        if (request.method != HttpMethod.POST.name()) {
            throw AuthenticationServiceException("Authentication method not supported: ${request.method}")
        }

        val loginMap: Map<String, String> = try {
            val messageBody = StreamUtils.copyToString(request.inputStream, StandardCharsets.UTF_8)
            jacksonObjectMapper().readValue(messageBody)
        } catch (e: Exception) {
            throw RuntimeException("Failed to parse login request", e)
        }

        val username = loginMap[usernameParameter]?.trim().orEmpty()
        val password = loginMap[passwordParameter].orEmpty()

        val authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password)
        setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }

    private fun setDetails(
        request: HttpServletRequest,
        authRequest: UsernamePasswordAuthenticationToken
    ) {
        authRequest.details = authenticationDetailsSource.buildDetails(request)
    }
}