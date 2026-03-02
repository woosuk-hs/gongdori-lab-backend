package hs.woosuk.gongdorilab.common.config

import hs.woosuk.gongdorilab.common.filter.JwtAuthenticationFilter
import hs.woosuk.gongdorilab.common.filter.LoginFilter
import hs.woosuk.gongdorilab.domain.jwt.service.TokenService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
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

    @Value($$"${remember_key}")
    private lateinit var key: String

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }

            .rememberMe { it
                .key("asdjFKlDfthdshfajfnfbAET")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(14 * 24 * 60 * 60)
            }

            .exceptionHandling { it
                .authenticationEntryPoint { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                }
                .accessDeniedHandler { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN)
                }
            }

            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            .authorizeHttpRequests { it
                .requestMatchers("/member/me").authenticated()
                .anyRequest().permitAll()
            }

            .addFilterBefore(JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter::class.java)

//            .addFilterBefore(
//               LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter::class.java
//            )

            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}