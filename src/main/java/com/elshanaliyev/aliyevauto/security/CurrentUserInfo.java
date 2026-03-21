package com.elshanaliyev.aliyevauto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrentUserInfo {
    private Long id;
    private String username;
}