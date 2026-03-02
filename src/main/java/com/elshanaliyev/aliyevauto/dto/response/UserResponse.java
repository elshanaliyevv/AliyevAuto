package com.elshanaliyev.aliyevauto.dto.response;

import com.elshanaliyev.aliyevauto.Enums.UserEnums.Roles;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String username;
    Roles role;
    String email;
    String number;

}
