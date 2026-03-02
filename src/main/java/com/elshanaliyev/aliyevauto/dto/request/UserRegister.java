package com.elshanaliyev.aliyevauto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegister {
    @NotBlank
    String username;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    String email;
    @Pattern(regexp = "^(?:\\+994|0)?(?:50|51|55|70|77|99|10)\\d{7}$")
    String number;
    @NotBlank
    String password;

}
