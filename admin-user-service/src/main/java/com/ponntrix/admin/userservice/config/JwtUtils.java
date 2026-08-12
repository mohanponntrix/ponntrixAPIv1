package com.ponntrix.admin.userservice.config;

import com.ponntrix.admin.userservice.entity.Role;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtils {

    @Value("${jwt.rsa.refresh-expiration}")
    private long refreshTokenExpirationMs;

    @Value("${jwt.rsa.expiration}")
    private long accessTokenExpirationMs;

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public JwtUtils(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String generateAccessToken(String username, List<String> roles, Long userId) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(publicKey) // Inject RSAPublicKey into JwtUtils
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


}
