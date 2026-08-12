package com.ponntrix.admin.userservice.service;

import com.ponntrix.admin.userservice.config.JwtUtils;
import com.ponntrix.admin.userservice.dto.AuthRequestDto;
import com.ponntrix.admin.userservice.dto.AuthResponseDto;
import com.ponntrix.admin.userservice.dto.RefreshTokenRequest;
import com.ponntrix.admin.userservice.entity.RefreshToken;
import com.ponntrix.admin.userservice.entity.Role;
import com.ponntrix.admin.userservice.entity.User;
import com.ponntrix.admin.userservice.repository.RefreshTokenRepository;
import com.ponntrix.admin.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.rsa.refresh-expiration}")
    private long refreshTokenExpirationMs;


    public AuthResponseDto login(AuthRequestDto request) {
        try{
            // 1. Authenticate credentials against Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            log.info("Authentication successful for user: {}", request.username());
        } catch (Exception e) {
            log.error("Authentication failed inside Spring Security: ", e); // <--- THIS WILL PRINT THE EXACT ERROR
            throw new RuntimeException(e);
        }

        // 2. Fetch user details from database
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.username()));

        log.info("User details for given credentials : {} ",user.getUsersId());

        List<String> roles = user.getRoles().stream()
                .map(Role::getRoleName) // or .map(role -> role.getRoleName())
                .toList();

        log.info("Role details for given user : {} ",roles);

        String accessToken = jwtUtils.generateAccessToken(user.getUsername(), roles, user.getUsersId().longValue());
        String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());

        log.info("=================================="+accessToken);

        log.info("=================================="+refreshToken);

        // 3. Save refresh token in DB
        saveRefreshToken(user, refreshToken);


        return new AuthResponseDto(
                accessToken,
                refreshToken,
                user.getUserType(),
                user.getUserId(),
                roles
        );

    }

    @Transactional
    public AuthResponseDto getRefreshToken(RefreshTokenRequest request) {
        String incomingRefreshToken = request.refreshToken();

        // 1. Extract username/subject from JWT claims
        String username = jwtUtils.extractUsername(incomingRefreshToken);

        // 2. Fetch user details from DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 3. Hash the incoming token to check existence in DB
        String tokenHash = hashToken(incomingRefreshToken);

        RefreshToken storedToken = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new RuntimeException("Invalid or revoked refresh token"));

        // 4. Verify token status and expiration
        if (storedToken.getIsRevoked() || storedToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new RuntimeException("Refresh token is expired or revoked");
        }

        // 5. Extract role names as List<String>
        List<String> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .toList();

        // 6. Issue NEW Access Token
        String newAccessToken = jwtUtils.generateAccessToken(
                user.getUsername(),
                roles,
                user.getUsersId().longValue()
        );

        // 7. Token Rotation: Issue a NEW Refresh Token and revoke/delete the old one
        String newRefreshToken = jwtUtils.generateRefreshToken(user.getUsername());

        // Revoke old token and save new token
        refreshTokenRepository.delete(storedToken);
        saveRefreshToken(user, newRefreshToken);

        // 8. Return updated response
        return new AuthResponseDto(
                newAccessToken,
                newRefreshToken,
                user.getUserType(),
                user.getUserId(),
                roles
        );
    }

    @Transactional
    public void saveRefreshToken(User user, String refreshToken) {
        // 1. Optionally revoke/delete old active tokens for this user
        refreshTokenRepository.deleteByUser(user);

        // 2. Build the RefreshToken entity
        RefreshToken refreshTokenEntity = new RefreshToken();

        refreshTokenEntity.setTokenHash(hashToken(refreshToken));
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setIsRevoked(false);
        refreshTokenEntity.setExpiresAt(OffsetDateTime.now().plusNanos(refreshTokenExpirationMs * 1_000_000));

        // 3. Save to PostgreSQL
        refreshTokenRepository.save(refreshTokenEntity);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing refresh token", e);
        }
    }
}
