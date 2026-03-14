package com.elshanaliyev.aliyevauto.model.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class TokensResponse {
    String accessToken;
    String refreshToken;
    Long accessTokenExpries;
    Long refreshTokenExpires;
}
