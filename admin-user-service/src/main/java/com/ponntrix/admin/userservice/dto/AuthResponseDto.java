package com.ponntrix.admin.userservice.dto;

import java.util.List;


public record AuthResponseDto(
     String jwtToken,
     String refreshToken,
     String userType,
     Integer userId,
     List<String>roles
) {
}
