package com.ponntrix.admin.userservice.repository;

import com.ponntrix.admin.userservice.entity.RefreshToken;
import com.ponntrix.admin.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    // Helper to revoke existing tokens for a user during new login
    @Modifying
    void deleteByUser(User user);

}
