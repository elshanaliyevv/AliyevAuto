package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.model.response.TokensResponse;

public interface JwtService {

    TokensResponse generateTokens(String username);

    String extractUsernameFromAccessToken(String accessToken);

    String extractUsernameFromRefreshToken(String refreshToken);

    boolean isValidAccess(String token);

    boolean isValidRefresh(String token);

    TokensResponse refreshAccessToken(String refreshToken);

    public Long extractIdFromRefreshToken(String refreshToken);

    public Long extractIdFromAccessToken(String accessToken);
}