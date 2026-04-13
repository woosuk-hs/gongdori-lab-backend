package hs.woosuk.gongdorilab.common.config

import hs.woosuk.gongdorilab.common.filter.JwtAuthenticationFilter
import hs.woosuk.gongdorilab.domain.jwt.service.TokenService
import hs.woosuk.gongdorilab.domain.member.entity.MemberRole
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
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
            .cors {  }
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
                    // 인증 없이 접근 가능
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/recruit").permitAll()
                    .requestMatchers(HttpMethod.GET, "/recruit/{studentId}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/members").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/test/**").permitAll()

                    .requestMatchers(HttpMethod.GET, "/neis/**").permitAll()

                    // 로그인 필요
                    .requestMatchers("/members/me").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/members/me").authenticated()

                    // 관리자 전용
                    .requestMatchers(HttpMethod.GET, "/recruit").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/recruit/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/recruit/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/members/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/members/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/members/**").hasRole("ADMIN")

                    .anyRequest().authenticated()
            }

            .addFilterBefore(
                JwtAuthenticationFilter(tokenService),
                UsernamePasswordAuthenticationFilter::class.java
            )

            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun roleHierarchy(): RoleHierarchy =
        RoleHierarchyImpl.withRolePrefix("ROLE_")
            .role(MemberRole.ADMIN.name).implies(MemberRole.MEMBER.name)
            .build()
}