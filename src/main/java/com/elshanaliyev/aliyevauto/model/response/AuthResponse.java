package com.elshanaliyev.aliyevauto.model.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {

    TokensResponse tokensResponse;
    UserResponse userResponse;
}
