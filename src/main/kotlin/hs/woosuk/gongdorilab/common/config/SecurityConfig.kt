package hs.woosuk.gongdorilab.common.config

import hs.woosuk.gongdorilab.common.filter.JwtAuthenticationFilter
import hs.woosuk.gongdorilab.domain.jwt.service.TokenService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenService: TokenService,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }

            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                }
                it.accessDeniedHandler { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN)
                }
            }

            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            .authorizeHttpRequests {
                it
                    // .requestMatchers("/member/me").authenticated()
                    .requestMatchers("/auth/**").permitAll()
                    // .anyRequest().authenticated()
                    .anyRequest().permitAll()

            }

            .addFilterBefore(
                JwtAuthenticationFilter(tokenService),
                UsernamePasswordAuthenticationFilter::class.java
            )

            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}