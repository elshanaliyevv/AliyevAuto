package com.elshanaliyev.aliyevauto.dto.request;

import com.elshanaliyev.aliyevauto.Enums.UserEnums.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLogin {
    @NotBlank
    String identifier;
    @NotBlank
    String password;
    @NotBlank
    @Enumerated(EnumType.STRING)
    Roles role;
}
