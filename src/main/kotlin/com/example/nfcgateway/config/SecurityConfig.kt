package com.example.nfcgateway.config

import com.example.nfcgateway.service.EmployeeService
import com.example.nfcgateway.util.JWTService
import com.example.nfcgateway.util.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
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
    private val employeeService: EmployeeService,
    private val jwtService: JWTService,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
//            .cors { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(
                        "/api/employee/change-password",
                        "/api/employee/login",
                        "/api/employee/nfc-login",
                        "/api/employee/{email}",
                    ).permitAll()
                    .requestMatchers("/api/admin/**"
                    ).hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

//    @Bean
//    fun authenticationManager(http: HttpSecurity, authenticationManagerBuilder: AuthenticationManagerBuilder): AuthenticationManager? {
//        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
//            .userDetailsService(employeeService)
//            .passwordEncoder(passwordEncoder())
//            .and()
//            .build()
//    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(employeeService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return ProviderManager(authProvider)
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtService, employeeService)
    }
}