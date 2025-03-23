package com.example.nfcgateway.util

import com.example.nfcgateway.service.EmployeeService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JWTService,
    private val employeeService: EmployeeService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if (request.requestURI.contains("/api/employee/login")) {
            filterChain.doFilter(request, response)
            return
        }
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.error("Authorization header is missing or invalid")
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        if (jwt.isBlank()){
            logger.error("JWT token is blank")
            filterChain.doFilter(request, response)
            return
        }
        try {
            logger.info("Validating JWT token: $jwt")
            if (jwtService.validateToken(jwt)) {
                val userEmail = jwtService.extractUsername(jwt)
                logger.info("Token validated for email: $userEmail")
                val userDetails = employeeService.loadUserByUsername(userEmail)

                // Log the authorities for debugging
                logger.info("User authorities: ${userDetails.authorities}")
                // Log the roles assigned to the user
                logger.info("User roles: ${userDetails.authorities}")

                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                SecurityContextHolder.getContext().authentication = authToken
                logger.info(" Security context updated for user: $userEmail")
            }else{
                logger.error("JWT validation failed")
            }
        } catch (e: Exception) {
            logger.error("JWT validation failed: ${e.message}")
        }

        filterChain.doFilter(request, response)
    }
}