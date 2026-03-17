package com.elshanaliyev.aliyevauto.model.response;

import jdk.jshell.Snippet;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    Long id;
    String username;
    String email;
    String number;
}
