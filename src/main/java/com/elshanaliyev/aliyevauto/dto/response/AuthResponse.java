package com.elshanaliyev.aliyevauto.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {

    TokensResponse tokensResponse;
    UserResponse userResponse;
}
