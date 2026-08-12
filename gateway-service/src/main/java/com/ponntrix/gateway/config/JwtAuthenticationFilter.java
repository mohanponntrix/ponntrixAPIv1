package com.ponntrix.gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RSAPublicKey publicKey;

    @Value("${jwt.secret:defaultSecretKey}")
    private String jwtSecret;

    private final List<String> openEndpoints = List.of(
            "/api/user/auth/login",
            "/api/user/auth/refresh"
    );

    public JwtAuthenticationFilter(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        log.info("Gateway received path : {},{}",path,path.startsWith("/api/user/auth/"));
        // Skip JWT validation for all public auth endpoints
        return path.startsWith("/api/user/auth/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();
//
//        // 1. Bypass authentication for public endpoints
//        if (openEndpoints.stream().anyMatch(requestPath::startsWith)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 2. Validate Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        try {
            // 3. Parse and Validate Token Signature + Expiration
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();


            // 1. Extract claims directly from the parsed token claims
            String username = claims.getSubject();
            Long userId = claims.get("userId", Long.class);

            // Extract roles list from JWT claims
            @SuppressWarnings("unchecked")
            List<String> roles = claims.get("roles", List.class);
            String rolesHeaderValue = (roles != null) ? String.join(",", roles) : "";

            // 2. Wrap the request to inject custom HTTP headers for downstream microservices
            HttpServletRequest mutatedRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if ("X-Auth-User".equalsIgnoreCase(name) || "X-User-Name".equalsIgnoreCase(name)) {
                        return username;
                    }
                    if ("X-User-Id".equalsIgnoreCase(name)) {
                        return String.valueOf(userId);
                    }
                    if ("X-Auth-Roles".equalsIgnoreCase(name)) {
                        return rolesHeaderValue;
                    }
                    return super.getHeader(name);
                }

                @Override
                public Enumeration<String> getHeaderNames() {
                    List<String> names = Collections.list(super.getHeaderNames());
                    names.add("X-Auth-User");
                    names.add("X-User-Id");
                    names.add("X-Auth-Roles");
                    return Collections.enumeration(names);
                }
            };

            // 4. Continue request chain if valid
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
        }
    }


}
