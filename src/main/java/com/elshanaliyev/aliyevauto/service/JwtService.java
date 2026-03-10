package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.dto.response.TokensResponse;

public interface JwtService {
    TokensResponse generateTokens(String username);
    String extractEmailFromAccessToken(String accessToken);
    String extractEmailFromRefreshToken(String refreshToken);
    boolean isValidAccess(String token);
    boolean isValidRefresh(String token);
    TokensResponse refreshAccessToken(String refreshToken);
}
